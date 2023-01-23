package personal.rowan.viewerapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Rowan Hall
 */
@Parcelize
data class SearchParameter(val selectedItems: List<String>, val elo: EloParameter?):
    Parcelable

enum class EloParameter(val minElo: Int, val maxElo: Int) {
    NONE(-1, -1),
    LOW(0, 1500),
    MID(1500, 1700),
    HIGH(1700, 3000)
}