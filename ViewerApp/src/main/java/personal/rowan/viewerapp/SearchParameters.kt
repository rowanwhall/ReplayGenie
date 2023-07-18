package personal.rowan.viewerapp

import android.os.Parcelable
import androidx.annotation.IdRes
import kotlinx.parcelize.Parcelize

/**
 * Created by Rowan Hall
 */
@Parcelize
data class SearchParameter(val format: FormatParameter, val selectedItems: List<String>, val elo: EloParameter?):
    Parcelable

enum class EloParameter(val minElo: Int, val maxElo: Int): ChipChoice {
    NONE(-1, -1) {
        @IdRes override fun resId(): Int {
            return R.id.chip_elo_none
        }
    },
    LOW(0, 1400) {
        @IdRes override fun resId(): Int {
            return R.id.chip_elo_low
        }
    },
    MID(1400, 1700) {
        @IdRes override fun resId(): Int {
            return R.id.chip_elo_mid
        }
    },
    HIGH(1700, 3000) {
        @IdRes override fun resId(): Int {
            return R.id.chip_elo_high
        }
    }
}

enum class FormatParameter(val argumentString: String): ChipChoice {
    REGULATION_D("gen9vgc2023regulationd") {
        @IdRes override fun resId(): Int {
            return R.id.chip_format_regd
        }
    },
    REGULATION_C("gen9vgc2023regulationc") {
        @IdRes override fun resId(): Int {
            return R.id.chip_format_regc
        }
    },
    SERIES_TWO("gen9vgc2023series2") {
        @IdRes override fun resId(): Int {
            return R.id.chip_format_series2
        }
    },
    SERIES_ONE("gen9vgc2023series1") {
        @IdRes override fun resId(): Int {
            return R.id.chip_format_series1
        }
    }
}