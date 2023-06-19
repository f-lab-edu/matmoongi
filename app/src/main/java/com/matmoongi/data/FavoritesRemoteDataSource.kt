package com.matmoongi.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/** firebase realtimeDB에서 즐겨찾기 리스트 가져오기 **/
class FavoritesRemoteDataSource(
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchFavoriteRestaurants(): List<Restaurant> =
        // Move the execution to an IO-optimized thread since the ApiService
        // doesn't support coroutines and makes synchronous requests.
        withContext(ioDispatcher) {
            TODO("즐겨찾기 리스트 받는 api")
        }
}
