package personal.rowan.viewerapp

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

/**
 * Created by Rowan Hall
 */
class EntryChipGroup<T: ChipData> : ChipGroup {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var data = listOf<T>()
    private var closeListener: CloseListener<T>? = null

    private fun notifyDataSetChanged() {
        removeAllViews()
        data.forEach { item ->
            val chip = Chip(context)
            chip.text = item.label()
            chip.isChipIconVisible = false
            chip.isCloseIconVisible = true
            chip.setOnCloseIconClickListener {
                removeView(chip)
                closeListener?.onClose(item)
            }
            chip.isClickable = true
            chip.isCheckable = false
            addView(chip)
        }
    }

    fun setData(data: List<T>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getCount(): Int {
        return data.size
    }

    fun setCloseListener(closeListener: CloseListener<T>) {
        this.closeListener = closeListener
    }
}

interface ChipData {
    fun label(): String
}

interface CloseListener<T: ChipData> {
    fun onClose(item: T)
}