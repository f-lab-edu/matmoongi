package com.matmoongi.data

/** firebase realtimeDB와 즐겨찾기 리스트 동기화 **/
class FavoritesRepository(
    private val favoritesRemoteDataSource: FavoritesRemoteDataSource,
) {
    private var favoriteRestaurants: List<Restaurant> = emptyList()

    private fun getFavoriteRestaurants() {
        TODO("api로 리스트 받아서 덮어쓰기")
    }

    private fun register() {
        TODO("api로 즐겨찾기에 음식점 추가하고 getFavoriteRestaurants() 호출 why? -> 즐겨찾기 업데이트")
    }

    private fun unregister() {
        TODO("api로 즐겨찾기에 음식점 추가하고 getFavoriteRestaurants() 호출 why? -> 즐겨찾기 업데이트")
    }
}
