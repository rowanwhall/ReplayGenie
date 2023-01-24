package personal.rowan.viewerapp

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

/**
 * Created by Rowan Hall
 */
@Parcelize
data class SearchParameter(val format: FormatParameter, val selectedItems: List<String>, val elo: EloParameter?):
    Parcelable

enum class EloParameter(val minElo: Int, val maxElo: Int) {
    NONE(-1, -1),
    LOW(0, 1400),
    MID(1400, 1700),
    HIGH(1700, 3000)
}

enum class FormatParameter(@StringRes val stringResource: Int, val argumentString: String) {
    SERIES_TWO(R.string.search_parameter_format_series2, "gen9vgc2023series2"),
    SERIES_ONE(R.string.search_parameter_format_series1, "gen9vgc2023series1")
}