package personal.rowan.viewerapp.searchresult

/**
 * Created by Rowan Hall
 */

data class SearchResultViewState(val items: List<SearchResultItemViewState>)

data class SearchResultItemViewState(val replayId: String, val team1: List<String>, val team2: List<String>, val highElo: Long)