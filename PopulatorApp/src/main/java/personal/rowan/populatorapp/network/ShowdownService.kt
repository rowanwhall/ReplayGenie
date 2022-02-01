package personal.rowan.populatorapp.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Rowan Hall
 */
interface ShowdownService {

    @GET("{id}.log")
    fun getReplayString(@Path("id") id: String): Call<String>

    @GET("search.json")
    fun getReplays(@Query("format") format: String = "gen8vgc2022", @Query("page") page: Int = 1): Call<List<Replay>>
}

data class Replay(val id: String, val uploadTime: Long)