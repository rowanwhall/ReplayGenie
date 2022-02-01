package personal.rowan.viewerapp

import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import personal.rowan.sharedmodule.Resource
import javax.inject.Inject

/**
 * Created by Rowan Hall
 */
@ExperimentalCoroutinesApi
@ActivityRetainedScoped
class SearchRepository @Inject constructor(private val fireStore: FirebaseFirestore){

    suspend fun getReplays(teamSearch: List<String>): Flow<Resource<SearchViewState>> {
        return callbackFlow {
            trySend(Resource.Loading())
            fireStore
                .collection("replay-data")
                .limit(50)
                .whereArrayContainsAny("allteams", teamSearch)
                .get()
                .addOnSuccessListener {
                    val result = mutableListOf<SearchItemViewState>()
                    it.documents.forEach { document ->
                        val data = document.data ?: return@forEach
                        val replay = data["replay"] as String
                        val team1 = data["team1"] as List<String>
                        val team2 = data["team2"] as List<String>
                        result.add(SearchItemViewState(replay, team1, team2))
                    }
                    trySend(Resource.Success(SearchViewState(result)))
                }
                .addOnFailureListener {
                    trySend(Resource.Error(it.localizedMessage ?: "firebase query error"))
                }
            awaitClose {
                channel.close()
            }
        }
    }
}