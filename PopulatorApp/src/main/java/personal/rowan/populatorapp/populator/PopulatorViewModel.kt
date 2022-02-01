package personal.rowan.populatorapp.populator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import personal.rowan.sharedmodule.Resource
import javax.inject.Inject

/**
 * Created by Rowan Hall
 */
@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class PopulatorViewModel @Inject constructor(
    private val repository: PopulatorRepository,
    application: Application
) : AndroidViewModel(application) {

    private val liveData: MutableLiveData<Resource<PopulatorViewState>> = MutableLiveData()
    private var currentPage = 1

    fun populateBackend() {
        viewModelScope.launch {
            repository.getReplayLogs(currentPage)
                .flatMapMerge {
                    repository.checkForDuplicate(it)
                }.flatMapMerge {
                    repository.uploadNewReplays(it)
                }
                .collect { liveData.value = it }
        }
    }

    fun changePage(page: Int) {
        currentPage = page
    }

    fun liveData(): LiveData<Resource<PopulatorViewState>> = liveData
}