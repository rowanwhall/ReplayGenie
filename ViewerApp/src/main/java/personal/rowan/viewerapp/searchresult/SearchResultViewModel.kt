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

    fun getReplays(teamSearch: List<String>) {
        viewModelScope.launch {
            repository.getReplays(teamSearch)
                .collect { liveData.value = it }
        }
    }

    fun liveData(): LiveData<Resource<SearchResultViewState>> = liveData
}