package personal.rowan.viewerapp.searchresult

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import personal.rowan.viewerapp.SearchParameter
import javax.inject.Inject

/**
 * Created by Rowan Hall
 */
@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val repository: SearchResultRepository,
    application: Application
) : AndroidViewModel(application) {

    private val pagedLiveData: MutableLiveData<PagingData<SearchResultItemViewState>> =
        MutableLiveData()

    fun getPagedFeed(parameter: SearchParameter) {
        viewModelScope.launch {
            repository.getFeedPaging(
                parameter.format.argumentString,
                parameter.selectedItems,
                parameter.elo?.minElo ?: -1,
                parameter.elo?.maxElo ?: 3000
            )
                .cachedIn(viewModelScope)
                .collect {
                    pagedLiveData.value = it
                }
        }
    }

    fun pagedLiveData(): LiveData<PagingData<SearchResultItemViewState>> = pagedLiveData
}