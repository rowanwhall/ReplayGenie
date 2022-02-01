package personal.rowan.viewerapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Rowan Hall
 */
class SearchAdapter(private var data: List<SearchItemViewState>): RecyclerView.Adapter<SearchViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(viewState: SearchViewState) {
        data = viewState.items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view_search, parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val team1View = itemView.findViewById<TextView>(R.id.tv_team1)
    private val team2View = itemView.findViewById<TextView>(R.id.tv_team2)
    private val replayLinkView = itemView.findViewById<TextView>(R.id.tv_replay_link)

    fun bind(viewState: SearchItemViewState) {
        team1View.text = viewState.team1.toString()
        team2View.text = viewState.team2.toString()
        replayLinkView.text = viewState.replayId
    }
}