package personal.rowan.populatorapp.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Rowan Hall
 */
interface JsonShowdownService {

    @GET("search.json")
    suspend fun getReplays(@Query("format") format: String = "gen8vgc2022",
                           @Query("page") page: Int = 1): Response<List<ReplayData>>
}

data class ReplayData(val id: String, val uploadTime: Long)