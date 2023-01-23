package personal.rowan.viewerapp.searchparameter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

/**
 * Created by Rowan Hall
 */

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class SearchParameterViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private val liveData: MutableLiveData<SearchParameterViewState> = MutableLiveData()

    fun selectItem(item: String) {
        val oldValue = getValue()
        oldValue.selectedItems[item] = SearchParameterItemViewState(item)
        liveData.value = oldValue
    }

    fun removeItem(item: String) {
        val oldValue = getValue()
        oldValue.selectedItems.remove(item)
        liveData.value = oldValue
    }

    fun setElo(elo: Long) {
        val oldValue = getValue()
        oldValue.highestElo = elo
        liveData.value = oldValue
    }

    fun getValue() = liveData.value ?: SearchParameterViewState(mutableMapOf(), -1)

    fun liveData(): LiveData<SearchParameterViewState> = liveData
}