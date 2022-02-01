package personal.rowan.populatorapp.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

/**
 * Created by Rowan Hall
 */
@Module
@InstallIn(SingletonComponent::class)
object ShowdownApiModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideScalarConverterFactory(): ScalarsConverterFactory = ScalarsConverterFactory.create()

    @Singleton
    @Provides
    fun provideJsonService(okHttpClient: OkHttpClient,
                           gsonConverterFactory: GsonConverterFactory): JsonShowdownService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
        return retrofit.create(JsonShowdownService::class.java)
    }

    @Singleton
    @Provides
    fun provideScalarService(okHttpClient: OkHttpClient,
                             scalarsConverterFactory: ScalarsConverterFactory): ScalarShowdownService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(scalarsConverterFactory)
            .build()
        return retrofit.create(ScalarShowdownService::class.java)
    }
}

const val BASE_URL = "https://replay.pokemonshowdown.com"