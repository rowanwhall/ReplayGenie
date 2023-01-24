package personal.rowan.populatorapp.populator

import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import personal.rowan.sharedmodule.Resource
import personal.rowan.populatorapp.network.BaseNetworkRepository
import personal.rowan.populatorapp.network.JsonShowdownService
import personal.rowan.populatorapp.ReplayParser
import personal.rowan.populatorapp.network.ScalarShowdownService
import personal.rowan.sharedmodule.DEFAULT_FORMAT

/**
 * Created by Rowan Hall
 */
@ExperimentalCoroutinesApi
@ActivityRetainedScoped
class PopulatorRepository @Inject constructor(
    private val scalarService: ScalarShowdownService,
    private val jsonService: JsonShowdownService,
    private val fireStore: FirebaseFirestore
) : BaseNetworkRepository() {

    suspend fun getReplayLogs(page: Int): Flow<Resource<PopulatorViewState>> {
        return flow {
            when (val listResponse = safeApiCall { jsonService.getReplays(format = DEFAULT_FORMAT, page = page) }) {
                is Resource.Success -> {
                    val logMap = mutableMapOf<String, String>()
                    listResponse.data!!.forEach {
                        val logResponse = safeApiCall { scalarService.getReplayLog(it.id) }
                        if (logResponse is Resource.Success) {
                            logMap[it.id] = logResponse.data!!
                        }
                    }
                    emit(
                        Resource.Success(
                            PopulatorViewState(
                                logMap.count(),
                                logMap,
                                listOf(),
                                -1,
                                page
                            )
                        )
                    )
                }
                is Resource.Loading -> {
                    emit(Resource.Loading())
                }
                is Resource.Error -> {
                    emit(Resource.Error(listResponse.message ?: ""))
                }
            }
        }
            .onStart { emit(Resource.Loading()) }
            .flowOn(Dispatchers.IO)
    }

    suspend fun checkForDuplicate(resource: Resource<PopulatorViewState>): Flow<Resource<PopulatorViewState>> {
        return callbackFlow {
            val dataToUpload = mutableListOf<Map<String, Any>>()

            fun onComplete() {
                val currentState = resource.data!!
                trySend(
                    Resource.Success(
                        PopulatorViewState(
                            currentState.replayDataCount,
                            currentState.logsToCheck,
                            dataToUpload,
                            currentState.successfulUploadCount,
                            currentState.currentPage
                        )
                    )
                )
            }

            when (resource) {
                is Resource.Loading -> {
                    trySend(Resource.Loading())
                    awaitClose {
                        channel.close()
                    }
                    return@callbackFlow
                }
                is Resource.Error -> {
                    trySend(Resource.Error("Firebase duplicate check failed"))
                    awaitClose {
                        channel.close()
                    }
                    return@callbackFlow
                }
                is Resource.Success -> {
                    val currentState = resource.data!!
                    val numToCheck = currentState.logsToCheck.size
                    val completeCount = AtomicInteger(0)

                    if (numToCheck <= 0) {
                        onComplete()
                        awaitClose {
                            channel.close()
                        }
                        return@callbackFlow
                    }

                    currentState.logsToCheck.keys.forEach { replayId ->
                        val replayUrl = "https://replay.pokemonshowdown.com/$replayId"
                        fireStore
                            .collection(DEFAULT_FORMAT)
                            .whereEqualTo("replay", replayUrl)
                            .get()
                            .addOnSuccessListener {
                                if (it.isEmpty) {
                                    val parsedReplay =
                                        ReplayParser().parseReplay(currentState.logsToCheck[replayId]!!)
                                    val allTeams = mutableListOf<String>()
                                    allTeams.addAll(parsedReplay.team1)
                                    allTeams.addAll(parsedReplay.team2)
                                    val replayData = hashMapOf(
                                        "replay" to replayUrl,
                                        "team1" to parsedReplay.team1,
                                        "team2" to parsedReplay.team2,
                                        "allteams" to allTeams,
                                        "highElo" to parsedReplay.highestElo
                                    )
                                    dataToUpload.add(replayData)
                                }
                                if (completeCount.incrementAndGet() >= numToCheck) {
                                    onComplete()
                                }
                            }
                            .addOnFailureListener {
                                if (completeCount.incrementAndGet() >= numToCheck) {
                                    onComplete()
                                }
                            }
                    }
                    awaitClose {
                        channel.close()
                    }
                }
            }
        }
    }

    suspend fun uploadNewReplays(resource: Resource<PopulatorViewState>): Flow<Resource<PopulatorViewState>> {
        return callbackFlow {
            val successCount = AtomicInteger(0)
            val completeCount = AtomicInteger(0)

            fun onComplete() {
                val currentState = resource.data!!
                trySend(
                    Resource.Success(
                        PopulatorViewState(
                            currentState.replayDataCount,
                            currentState.logsToCheck,
                            currentState.dataToUpload,
                            successCount.get(),
                            currentState.currentPage
                        )
                    )
                )
            }

            when (resource) {
                is Resource.Loading -> {
                    trySend(Resource.Loading())
                    awaitClose {
                        channel.close()
                    }
                    return@callbackFlow
                }
                is Resource.Error -> {
                    trySend(Resource.Error("upload failed"))
                    awaitClose {
                        channel.close()
                    }
                    return@callbackFlow
                }
                is Resource.Success -> {
                    val currentState = resource.data!!
                    val numToUpload = currentState.dataToUpload.size

                    if (numToUpload <= 0) {
                        onComplete()
                        awaitClose {
                            channel.close()
                        }
                        return@callbackFlow
                    }

                    currentState.dataToUpload.forEach { replayData ->
                        fireStore
                            .collection(DEFAULT_FORMAT)
                            .add(replayData)
                            .addOnSuccessListener {
                                successCount.incrementAndGet()
                                if (completeCount.incrementAndGet() >= numToUpload) {
                                    onComplete()
                                }
                            }
                            .addOnFailureListener {
                                if (completeCount.incrementAndGet() >= numToUpload) {
                                    onComplete()
                                }
                            }
                    }
                    awaitClose {
                        channel.close()
                    }
                }
            }
        }
    }
}