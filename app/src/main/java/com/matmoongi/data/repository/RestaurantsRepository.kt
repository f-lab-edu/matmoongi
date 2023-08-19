package com.matmoongi.data.repository

import android.net.Uri
import com.matmoongi.BuildConfig
import com.matmoongi.DistanceCalculator
import com.matmoongi.data.dataclass.Coordinate
import com.matmoongi.data.dataclass.SearchRestaurant
import com.matmoongi.data.datasource.RestaurantsRemoteDataSource
import com.matmoongi.response.Place
import java.util.Locale

private const val LATITUDE_LONGITUDE_PARAMETER_FORMAT = "%.7f,%.7f"

class RestaurantsRepository(
    private val restaurantsRemoteDataSource: RestaurantsRemoteDataSource,
) {
    suspend fun fetchCurrentLocation(): Result<Coordinate> =
        restaurantsRemoteDataSource.fetchCurrentGeoLocation()

    suspend fun fetchNearbyRestaurant(coordinate: Coordinate): List<SearchRestaurant> {
        val location = LATITUDE_LONGITUDE_PARAMETER_FORMAT.format(
            Locale.ROOT,
            coordinate.latitude,
            coordinate.longitude,
        )

        val placeList = restaurantsRemoteDataSource.fetchNearbyRestaurants(location)

        return placeList.map { place ->
            placeToSearchRestaurant(place, coordinate)
        }
    }

    private fun placeToSearchRestaurant(place: Place, coordinate: Coordinate): SearchRestaurant {
        val imageUrl = getPhotoImageUrl(place)

        return SearchRestaurant(
            placeId = place.placeId,
            name = place.name,
            thumbnailPhoto = imageUrl,
            rating = place.rating,
            ratingCount = place.userRatingsTotal,
            coordinate = place.geometry.location,
            distance = DistanceCalculator.calculateDistance(
                coordinate.latitude,
                coordinate.longitude,
                place.geometry.location.latitude,
                place.geometry.location.longitude,
            ),
        )
    }

    private fun getPhotoImageUrl(place: Place): String? =
        if (place.photos.isNullOrEmpty()) {
            null
        } else {
            getPhotoRequestUrl(
                photoReference = place.photos[0].photoReference,
                googleApiKey = BuildConfig.GOOGLE_PLACE_API_KEY,
            )
        }

    /** google placePhoto 사진 요청 url <br>
     * @see <a href="https://developers.google.com/maps/documentation/places/web-service/photos?hl=ko#place_photo_example">구글 PlacePhoto 요청 예시</a>
     * @param photoReference 사진 요청을 실행할 때 사진을 식별하는 데 사용되는 문자열
     * @param maxWidth 1이상 1600이하의 Int
     * @param maxHeight 1이상 1600이하의 Int
     * @param googleApiKey Google API Key 문자열
     * */
    private fun getPhotoRequestUrl(
        photoReference: String,
        maxWidth: String = "500",
        maxHeight: String = "500",
        googleApiKey: String,
    ): String {
        val builder = Uri.parse("https://maps.googleapis.com/").buildUpon()
        builder.appendPath("maps/api/place/photo")
            .appendQueryParameter("photoReference", photoReference)
            .appendQueryParameter("maxWidth", maxWidth)
            .appendQueryParameter("maxHeight", maxHeight)
            .appendQueryParameter("googleApiKey", googleApiKey)

        return builder.build().toString()
    }
}
