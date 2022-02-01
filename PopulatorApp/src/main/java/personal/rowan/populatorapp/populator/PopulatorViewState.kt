package personal.rowan.populatorapp.populator

/**
 * Created by Rowan Hall
 */
data class PopulatorViewState(
    // number of replay IDs fetched in last page
    val replayDataCount: Int,
    // ID -> log map successfully fetched from IDs
    val logsToCheck: Map<String, String>,
    // non-duplicated replay data to upload
    val dataToUpload: List<Map<String, Any>>,
    // number of successfully uploaded replays
    val successfulUploadCount: Int,
    // page of last parsed replay
    val currentPage: Int
)