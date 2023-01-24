package personal.rowan.viewerapp.searchparameter

import personal.rowan.viewerapp.EloParameter
import personal.rowan.viewerapp.FormatParameter

/**
 * Created by Rowan Hall
 */
data class SearchParameterViewState(
    val format: FormatParameter,
    val selectedItems: MutableMap<String, SearchParameterItemViewState>,
    val eloParameter: EloParameter?
)

data class SearchParameterItemViewState(val item: String)