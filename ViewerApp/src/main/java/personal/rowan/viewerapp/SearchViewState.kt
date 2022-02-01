package personal.rowan.viewerapp

/**
 * Created by Rowan Hall
 */
data class SearchViewState(val items: List<SearchItemViewState>)

data class SearchItemViewState(val replayId: String, val team1: List<String>, val team2: List<String>)