package personal.rowan.viewerapp.searchresult

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import personal.rowan.sharedmodule.Resource
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

    private val liveData: MutableLiveData<Resource<SearchResultViewState>> = MutableLiveData()

    fun getReplays(parameter: SearchParameter) {
        viewModelScope.launch {
            repository.getReplays(parameter.selectedItems, parameter.elo?.minElo ?: -1, parameter.elo?.maxElo ?: 3000)
                .collect { liveData.value = it }
        }
    }

    fun liveData(): LiveData<Resource<SearchResultViewState>> = liveData
}