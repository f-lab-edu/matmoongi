package com.matmoongi.data

import com.matmoongi.BuildConfig
import com.matmoongi.DistanceCalculator
import com.matmoongi.LocationResultCallback

class RestaurantsRepository(
    private val restaurantsRemoteDataSource: RestaurantsRemoteDataSource,
) {
    fun fetchCurrentLatLng(locationResultCallback: LocationResultCallback) =
        restaurantsRemoteDataSource.fetchCurrentLatLng(locationResultCallback)

    suspend fun fetchNearbyRestaurant(coordinate: Coordinate): List<SearchRestaurant> {
        return restaurantsRemoteDataSource.fetchNearbyRestaurants(coordinate).map { place ->
            val imageUrl = if (place.photos.isNullOrEmpty()) {
                null
            } else {
                restaurantsRemoteDataSource.getPhotoRequestUrl(
                    place.photos[0].photo_reference,
                    500,
                    500,
                    BuildConfig.GOOGLE_PLACE_API_KEY,
                )
            }
            SearchRestaurant(
                placeId = place.place_id,
                name = place.name,
                thumbnailPhoto = imageUrl,
                rating = place.rating,
                ratingCount = place.user_ratings_total,
                distance = DistanceCalculator.calculateDistance(
                    coordinate.lat,
                    coordinate.lng,
                    place.geometry.location.lat,
                    place.geometry.location.lng,
                ),
            )
        }
    }
}
