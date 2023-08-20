package com.matmoongi.data

import com.matmoongi.BuildConfig
import com.matmoongi.DistanceCalculator

class RestaurantsRepository(
    private val restaurantsRemoteDataSource: RestaurantsRemoteDataSource,
) {
    suspend fun fetchCurrentLocation(): Result<Coordinate> =
        restaurantsRemoteDataSource.fetchCurrentGeoLocation()

    suspend fun fetchNearbyRestaurant(coordinate: Coordinate): List<SearchRestaurant> {
        return restaurantsRemoteDataSource.fetchNearbyRestaurants(coordinate).map { place ->
            val imageUrl = if (place.photos.isNullOrEmpty()) {
                null
            } else {
                restaurantsRemoteDataSource.getPhotoRequestUrl(
                    place.photos[0].photoReference,
                    "500",
                    "500",
                    BuildConfig.GOOGLE_PLACE_API_KEY,
                )
            }
            SearchRestaurant(
                placeId = place.placeId,
                name = place.name,
                thumbnailPhoto = imageUrl,
                rating = place.rating,
                ratingCount = place.userRatingsTotal,
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
