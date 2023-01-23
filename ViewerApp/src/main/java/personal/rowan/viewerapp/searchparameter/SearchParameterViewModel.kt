package personal.rowan.viewerapp.searchparameter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import personal.rowan.viewerapp.EloParameter
import personal.rowan.viewerapp.SearchParameter
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

    fun setElo(eloParameter: EloParameter?) {
        liveData.value = SearchParameterViewState(getValue().selectedItems, eloParameter)
    }

    fun getParameters(): SearchParameter {
        val currentValue = getValue()
        return SearchParameter(currentValue.selectedItems.keys.toList(), currentValue.eloParameter)
    }

    private fun getValue() = liveData.value ?: SearchParameterViewState(mutableMapOf(), EloParameter.NONE)

    fun liveData(): LiveData<SearchParameterViewState> = liveData
}