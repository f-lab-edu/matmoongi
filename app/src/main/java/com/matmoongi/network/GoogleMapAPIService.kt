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

        private const val photoRequestUrl =
            baseUrl + "maps/api/place/photo?photo_reference=%s&maxwidth=%d&maxheight=%d&key=%s"

        /** google placePhoto 사진 요청 url <br>
         * @see <a href="https://developers.google.com/maps/documentation/places/web-service/photos?hl=ko#place_photo_example">구글 PlacePhoto 요청 예시</a>
         * @param photoReference 사진 요청을 실행할 때 사진을 식별하는 데 사용되는 문자열
         * @param maxWidth 1이상 1600이하의 Int
         * @param maxHeight 1이상 1600이하의 Int
         * @param googleApiKey Google API Key 문자열
         * */
        fun getPhotoRequestUrl(
            photoReference: String,
            maxWidth: Int,
            maxHeight: Int,
            googleApiKey: String,
        ) =
            String.format(photoRequestUrl, photoReference, maxWidth, maxHeight, googleApiKey)
    }
}
