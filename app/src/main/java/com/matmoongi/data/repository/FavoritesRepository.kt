package com.matmoongi.data.repository

import com.matmoongi.data.dataclass.Restaurant
import com.matmoongi.data.datasource.FavoritesRemoteDataSource

/** firebase realtimeDB와 즐겨찾기 리스트 동기화 **/
class FavoritesRepository(
    private val favoritesRemoteDataSource: FavoritesRemoteDataSource,
) {

    suspend fun getFavoriteRestaurants(): List<Restaurant> =
        favoritesRemoteDataSource.getFavoriteRestaurants()

    fun register() {
        TODO("firebase 즐겨찾기리스트에 음식점 추가하는 api 호출")
    }

    fun unregister() {
        TODO("firebase 즐겨찾기리스트에 음식점 제거하는 api 호출")
    }
}
