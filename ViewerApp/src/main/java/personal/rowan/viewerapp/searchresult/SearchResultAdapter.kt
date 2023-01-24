package personal.rowan.viewerapp.searchresult

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import personal.rowan.viewerapp.R

/**
 * Created by Rowan Hall
 */
class SearchResultAdapter :
    ListAdapter<SearchResultItemViewState, SearchResultViewHolder>(SearchResultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_search_result, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SearchResultDiffCallback : DiffUtil.ItemCallback<SearchResultItemViewState>() {
    override fun areItemsTheSame(
        oldItem: SearchResultItemViewState,
        newItem: SearchResultItemViewState
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: SearchResultItemViewState,
        newItem: SearchResultItemViewState
    ): Boolean {
        return oldItem.replayId == newItem.replayId
    }
}

class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val team1View = itemView.findViewById<TextView>(R.id.tv_team1)
    private val team2View = itemView.findViewById<TextView>(R.id.tv_team2)
    private val replayLinkView = itemView.findViewById<TextView>(R.id.tv_replay_link)
    private val highEloLink = itemView.findViewById<TextView>(R.id.tv_high_elo)

    fun bind(viewState: SearchResultItemViewState) {
        team1View.text = viewState.team1.toString()
        team2View.text = viewState.team2.toString()
        replayLinkView.text = viewState.replayId
        val highElo = viewState.highElo
        highEloLink.text = if (highElo > 0) highElo.toString() else "No ELO Data"
    }
}