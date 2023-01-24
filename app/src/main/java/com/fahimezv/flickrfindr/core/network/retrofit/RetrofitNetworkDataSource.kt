package com.fahimezv.flickrfindr.core.network.retrofit

import com.fahimezv.flickrfindr.core.network.NetworkDataSource
import com.fahimezv.flickrfindr.core.network.model.SearchDao
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private interface RetrofitNetworkApi {

    @GET("?method=flickr.photos.search&api_key=1508443e49213ff84d566777dc211f2a&format=json&nojsoncallback=1")
    suspend fun search(
        @Query("text") text: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): SearchDao

}

class RetrofitNetworkDataSource : NetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl("https://www.flickr.com/services/rest/")
        .client(
            OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(
                    HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
                )
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitNetworkApi::class.java)

    override suspend fun search(
        term: String,
        page: Int,
        perPage: Int,
    ) = networkApi.search(text = term, page = page, perPage = perPage)
}