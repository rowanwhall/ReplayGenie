package personal.rowan.viewerapp.searchparameter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import personal.rowan.viewerapp.EloParameter
import personal.rowan.viewerapp.FormatParameter
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

    fun selectItem(item: String, clear: Boolean = false) {
        val oldValue = getValue()
        if (clear) oldValue.selectedItems.clear()
        oldValue.selectedItems[item] = SearchParameterItemViewState(item)
        liveData.value = oldValue
    }

    fun removeItem(item: String) {
        val oldValue = getValue()
        oldValue.selectedItems.remove(item)
        liveData.value = oldValue
    }

    fun setFormat(formatParameter: FormatParameter) {
        val oldValue = getValue()
        liveData.value = SearchParameterViewState(formatParameter, oldValue.selectedItems, oldValue.eloParameter)
    }

    fun setElo(eloParameter: EloParameter?) {
        val oldValue = getValue()
        liveData.value = SearchParameterViewState(oldValue.format, oldValue.selectedItems, eloParameter)
    }

    fun getParameters(): SearchParameter {
        val currentValue = getValue()
        return SearchParameter(currentValue.format, currentValue.selectedItems.keys.toList(), currentValue.eloParameter)
    }

    fun getValue() = liveData.value ?: SearchParameterViewState(FormatParameter.REGULATION_D, mutableMapOf(), null)

    fun liveData(): LiveData<SearchParameterViewState> = liveData
}