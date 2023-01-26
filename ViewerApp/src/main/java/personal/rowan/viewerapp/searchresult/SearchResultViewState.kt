package personal.rowan.viewerapp.searchresult

/**
 * Created by Rowan Hall
 */

data class SearchResultViewState(val items: List<SearchResultItemViewState>)

data class SearchResultItemViewState(val replayId: String, val team1: List<String>, val team2: List<String>, val highElo: Long) {
    fun team1String(): String {
        return stringListDisplay(team1)
    }

    fun team2String(): String {
        return stringListDisplay(team2)
    }

    private fun stringListDisplay(stringList: List<String>): String {
        return stringList.toString()
            .replace(",", "")
            .replace("[", "")
            .replace("]", "")
    }
}