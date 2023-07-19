package personal.rowan.viewerapp.searchresult

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import personal.rowan.sharedmodule.DEFAULT_FORMAT

const val PAGE_SIZE = 50

/**
 * Created by Rowan Hall
 */
class SearchResultPagingSource(
    private val fireStore: FirebaseFirestore,
    private val format: String = DEFAULT_FORMAT,
    private val teamSearch: List<String>,
    private val minElo: Int,
    private val maxElo: Int) : PagingSource<QuerySnapshot, SearchResultItemViewState>() {

    override fun getRefreshKey(state: PagingState<QuerySnapshot, SearchResultItemViewState>): QuerySnapshot? {
        return null
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, SearchResultItemViewState> {
        return try {
            val query = fireStore
                .collection(format)
                .limit(PAGE_SIZE.toLong())
                .whereArrayContainsAny("allteams", teamSearch)
                .whereGreaterThanOrEqualTo("highElo", minElo)
                .whereLessThanOrEqualTo("highElo", maxElo)
                .orderBy("highElo", Query.Direction.DESCENDING)
            val currentPage = params.key ?: query.get().await()
            val nextPage = if (currentPage.isEmpty) {
                null
            } else {
                val lastVisibleItem = currentPage.documents[currentPage.size() - 1]
                query.startAfter(lastVisibleItem).get().await()
            }
            val result = mutableListOf<SearchResultItemViewState>()
            currentPage.documents.forEach { document ->
                val data = document.data ?: return@forEach
                val replay = data["replay"] as String
                val team1 = data["team1"] as List<String>
                val team2 = data["team2"] as List<String>
                val highElo = data["highElo"] as Long
                result.add(SearchResultItemViewState(replay, team1, team2, highElo))
            }
            LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}