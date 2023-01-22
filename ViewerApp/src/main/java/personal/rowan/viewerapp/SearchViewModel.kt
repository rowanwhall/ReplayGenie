package personal.rowan.viewerapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import personal.rowan.sharedmodule.Resource
import javax.inject.Inject

/**
 * Created by Rowan Hall
 */
@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
    application: Application
) : AndroidViewModel(application) {

    private val liveData: MutableLiveData<Resource<SearchViewState>> = MutableLiveData()

    fun getReplays(teamSearch: String) {
        viewModelScope.launch {
            repository.getReplays(listOf(teamSearch))
                .collect { liveData.value = it }
        }
    }

    fun liveData(): LiveData<Resource<SearchViewState>> = liveData
}