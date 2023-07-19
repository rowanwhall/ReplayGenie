package personal.rowan.viewerapp.searchresult

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import personal.rowan.viewerapp.R

/**
 * Created by Rowan Hall
 */
class SearchResultPagingAdapter: PagingDataAdapter<SearchResultItemViewState, SearchResultViewHolder>(SearchResultDiffCallback()) {
    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_search_result, parent, false)
        )
    }

    override fun onViewRecycled(holder: SearchResultViewHolder) {
        holder.recycle()
        super.onViewRecycled(holder)
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
    private val replayLinkView = itemView.findViewById<Button>(R.id.btn_replay_link)
    private val highEloHeader = itemView.findViewById<TextView>(R.id.tv_high_elo)

    fun bind(viewState: SearchResultItemViewState) {
        team1View.text = viewState.team1String()
        team2View.text = viewState.team2String()
        replayLinkView.setOnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW)
            webIntent.data = Uri.parse(viewState.replayId)
            it.context.startActivity(webIntent)
        }
        val highElo = viewState.highElo
        highEloHeader.context.let {
            highEloHeader.text = if (highElo > 0)
                it.getString(R.string.search_result_elo_header, highElo.toString()) else
                it.getString(R.string.search_result_elo_missing)
        }
    }

    fun recycle() {
        replayLinkView.setOnClickListener(null)
    }
}