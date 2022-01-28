package personal.rowan.showdownmetrics

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Created by Rowan Hall
 */
object RetrofitServiceFactory {

    fun <T> createScalarRetrofitService(clazz: Class<T>, endpoint: String, client: OkHttpClient): T {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(endpoint)
            .client(client)
            .build()

        return retrofit.create(clazz)
    }

    fun <T> createJsonRetrofitService(clazz: Class<T>, endpoint: String, client: OkHttpClient, gson: Gson): T {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(endpoint)
            .client(client)
            .build()

        return retrofit.create(clazz)
    }
}