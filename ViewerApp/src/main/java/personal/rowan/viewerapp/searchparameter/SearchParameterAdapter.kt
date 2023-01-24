package personal.rowan.viewerapp.searchparameter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import personal.rowan.viewerapp.R

/**
 * Created by Rowan Hall
 */
class SearchParameterAdapter(private val listener: SearchParameterListener) :
    ListAdapter<SearchParameterItemViewState, SearchParameterViewHolder>(SearchParameterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchParameterViewHolder {
        return SearchParameterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_search_parameter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchParameterViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    override fun onViewRecycled(holder: SearchParameterViewHolder) {
        holder.recycle()
        super.onViewRecycled(holder)
    }
}

class SearchParameterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val selectedItemText = itemView.findViewById<TextView>(R.id.tv_selected_item)
    private val removeItemImage = itemView.findViewById<ImageView>(R.id.iv_remove_item)

    fun bind(viewState: SearchParameterItemViewState, listener: SearchParameterListener) {
        selectedItemText.text = viewState.item
        removeItemImage.setOnClickListener {
            listener.onItemRemoved(viewState)
        }
    }

    fun recycle() {
        removeItemImage.setOnClickListener(null)
    }
}

class SearchParameterDiffCallback : DiffUtil.ItemCallback<SearchParameterItemViewState>() {
    override fun areItemsTheSame(
        oldItem: SearchParameterItemViewState,
        newItem: SearchParameterItemViewState
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: SearchParameterItemViewState,
        newItem: SearchParameterItemViewState
    ): Boolean {
        return oldItem.item == newItem.item
    }
}

interface SearchParameterListener {
    fun onItemRemoved(viewState: SearchParameterItemViewState)
}