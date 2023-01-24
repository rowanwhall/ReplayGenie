package personal.rowan.populatorapp

/**
 * Created by Rowan Hall
 */

const val END_DELIMITER = "|teampreview|"

class ReplayParser {

    fun parseReplay(replayString: String): ParsedReplay {
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

        val regex = "<strong>(.*)</strong>".toRegex()
        val ratings = regex.findAll(replayString)
            .map { it.groupValues[1] }
            .filter { isNumeric(it) }
            .map { it.toInt() }
        val maxRating = ratings.maxOrNull() ?: -1

        return ParsedReplay(team1, team2, maxRating)
    }
}

private fun isNumeric(toCheck: String): Boolean {
    val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
    return toCheck.matches(regex)
}

data class ParsedReplay(
    val team1: List<String>,
    val team2: List<String>,
    val highestElo: Int
)