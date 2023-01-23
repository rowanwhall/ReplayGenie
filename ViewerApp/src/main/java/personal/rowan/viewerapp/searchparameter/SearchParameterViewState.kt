package personal.rowan.viewerapp.searchparameter

/**
 * Created by Rowan Hall
 */
data class SearchParameterViewState(val selectedItems: MutableMap<String, SearchParameterItemViewState>, var highestElo: Long)

data class SearchParameterItemViewState(val item: String)