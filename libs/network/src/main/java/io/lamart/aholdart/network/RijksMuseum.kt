package io.lamart.aholdart.network

import io.lamart.aholdart.network.types.ArtCollection
import io.lamart.aholdart.network.types.ArtDetails
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RijksMuseum {

    @GET("collection")
    suspend fun getCollection(
        @Query("p") page: Int = 0,
        @Query("ps") pageSize: Int = 10
    ): ArtCollection

    @GET("collection/{objectNumber}")
    suspend fun getDetails(@Path("objectNumber") objectNumber: String): ArtDetails

    companion object {

        operator fun invoke(
            key: String,
            url: String = "https://www.rijksmuseum.nl/api/en/"
        ): RijksMuseum {
            val client = OkHttpClient.Builder()
                .addInterceptor(KeyInterceptor(key))
                .build()

            return Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RijksMuseum::class.java)
        }

    }

}