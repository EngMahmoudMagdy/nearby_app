package com.magdy.nearby.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.magdy.nearby.data.network.response.PhotoResponse
import com.magdy.nearby.data.network.response.VenueResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val CLIENT_ID = "FXP45FNMK0BE0LKTRZF2HAA5TGU3AJTQAHXBEHAALSYBO05O"
const val CLIENT_SECRET = "IXEVSCLH444IEOS0BDOJA2PQ3SEAWBOQE3EBRY4ZVVDERHF4"
const val VERSION_API = "20200207"
const val RADIUS_RANGE = "1000"

interface ApiService {
    @GET("explore")
    fun getVenueList(
        @Query("ll") latLng: String,
        @Query("radius") radiusRange: String = RADIUS_RANGE
    ): Deferred<VenueResponse>

    @GET("{id}/photos")
    fun getVenuePhotos(
        @Path("id") venueId: String
    ): Deferred<PhotoResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApiService {
            val requestInterceptor = Interceptor {
                val url = it.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("client_id", CLIENT_ID)
                    .addQueryParameter("client_secret", CLIENT_SECRET)
                    .addQueryParameter("v", VERSION_API)
                    .build()
                val request = it.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor it.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.foursquare.com/v2/venues/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)

        }
    }
}