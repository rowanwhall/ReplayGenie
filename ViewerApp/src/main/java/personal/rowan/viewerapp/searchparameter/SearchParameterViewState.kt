package personal.rowan.viewerapp.searchparameter

import personal.rowan.viewerapp.EloParameter

/**
 * Created by Rowan Hall
 */
data class SearchParameterViewState(val selectedItems: MutableMap<String, SearchParameterItemViewState>, val eloParameter: EloParameter?)

data class SearchParameterItemViewState(val item: String)