package com.matmoongi.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RestaurantsRemoteDataSource(
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun fetchNearbyRestaurants(): List<Restaurant> =
        // Move the execution to an IO-optimized thread since the ApiService
        // doesn't support coroutines and makes synchronous requests.
        withContext(ioDispatcher) {
            TODO("음식점 리스트 받는 api")
        }
}
