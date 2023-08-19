package com.matmoongi.network

import com.matmoongi.response.NearbyPlacesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleMapAPIService {
    @GET("maps/api/place/nearbysearch/json")
    suspend fun fetchNearbyRestaurants(
        @Query("keyword") keyword: String = "맛집",
        @Query("location") location: String,
        @Query("rankby") rankby: String = "distance",
        @Query("type") type: String = "restaurant",
        @Query("language") language: String = "ko",
        @Query("key") apiKey: String,
    ): NearbyPlacesResponse

    companion object {
        private const val baseUrl = "https://maps.googleapis.com/"
        private val googleMapAPIService =
            ServiceFactory.createRetrofitService<GoogleMapAPIService>(baseUrl)

        fun getService() = googleMapAPIService
    }
}
