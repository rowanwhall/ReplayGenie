package personal.rowan.viewerapp

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.IdRes
import com.google.android.material.chip.ChipGroup

/**
 * Created by Rowan Hall
 *
 * Specifically for use with ChipGroups set to singleSelection=true. Multi-choice would require
 * tracking all possible chips and un-checking them based on state object.
 */
class SingleChoiceChipGroup : ChipGroup {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var currentChoice: ChipChoice? = null

    private fun notifyDataSetChanged() {
        currentChoice?.let {
            check(it.resId())
        }
    }

    fun selectChoice(choice: ChipChoice?) {
        if (currentChoice == choice) return
        currentChoice = choice
        notifyDataSetChanged()
    }
}

interface ChipChoice {
    @IdRes fun resId(): Int
}