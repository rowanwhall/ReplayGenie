package personal.rowan.viewerapp.searchresult

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import personal.rowan.sharedmodule.DEFAULT_FORMAT
import javax.inject.Inject

/**
 * Created by Rowan Hall
 */
@ExperimentalCoroutinesApi
@ActivityRetainedScoped
class SearchResultRepository @Inject constructor(private val fireStore: FirebaseFirestore) {

    fun getFeedPaging(format: String = DEFAULT_FORMAT, teamSearch: List<String>, minElo: Int, maxElo: Int): Flow<PagingData<SearchResultItemViewState>> {
        return Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true
            )
        ) {
            SearchResultPagingSource(fireStore, format, teamSearch, minElo, maxElo)
        }.flow
    }
}