package personal.rowan.populatorapp.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Rowan Hall
 */
interface ScalarShowdownService {

    @GET("{id}.log")
    suspend fun getReplayLog(@Path("id") id: String): Response<String>
}