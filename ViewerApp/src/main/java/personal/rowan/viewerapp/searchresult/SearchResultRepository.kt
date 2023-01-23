package personal.rowan.viewerapp.searchresult

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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
class SearchResultRepository @Inject constructor(private val fireStore: FirebaseFirestore){

    @Suppress("UNCHECKED_CAST")
    suspend fun getReplays(teamSearch: List<String>, minElo: Int, maxElo: Int): Flow<Resource<SearchResultViewState>> {
        return callbackFlow {
            trySend(Resource.Loading())
            fireStore
                .collection("replay-data")
                .limit(50)
                .whereArrayContainsAny("allteams", teamSearch)
                .whereGreaterThanOrEqualTo("highElo", minElo)
                .whereLessThanOrEqualTo("highElo", maxElo)
                .orderBy("highElo", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    val result = mutableListOf<SearchResultItemViewState>()
                    it.documents.forEach { document ->
                        val data = document.data ?: return@forEach
                        val replay = data["replay"] as String
                        val team1 = data["team1"] as List<String>
                        val team2 = data["team2"] as List<String>
                        val highElo = data["highElo"] as Long
                        result.add(SearchResultItemViewState(replay, team1, team2, highElo))
                    }
                    trySend(Resource.Success(SearchResultViewState(result)))
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