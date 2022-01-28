package personal.rowan.showdownmetrics

import android.util.Log

/**
 * Created by Rowan Hall
 */

const val END_DELIMITER = "|teampreview|"

class ReplayParser {

    fun parseReplay(replayString: String): Pair<List<String>, List<String>> {
        val team1 = mutableListOf<String>()
        val team2 = mutableListOf<String>()

        val teamString = replayString
            .replaceBefore("|poke|p1|", "")
            .replaceAfter(END_DELIMITER, "")
            .replace(END_DELIMITER, "")
            .replace(", F", "")
            .replace(", M", "")
            .replace(", L50", "")

        teamString.substringBefore("|poke|p2")
            .replace("|poke|p1|", "")
            .split("|")
            .filter { it.isNotBlank() }
            .forEach {
                team1.add(it)
            }

        teamString.substringAfter("|poke|p2|")
            .replace("|poke|p2|", "")
            .split("|")
            .filter { it.isNotBlank() }
            .forEach {
                team2.add(it)
            }

        return Pair(team1, team2)
    }

}