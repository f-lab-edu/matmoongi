package com.matmoongi.data

class RestaurantsRepository(
    private val restaurantsRemoteDataSource: RestaurantsRemoteDataSource,
) {
    suspend fun getNearbyRestaurants(): List<Restaurant> =
        restaurantsRemoteDataSource.getNearbyRestaurants()
}
