package com.matmoongi.data

class RestaurantsRepository(
    private val restaurantsRemoteDataSource: RestaurantsRemoteDataSource,
) {
    private var restaurants: List<Restaurant> = emptyList()

    fun getNewRestaurants() {
        TODO("api로 장소들 받아서 덮어쓰기")
    }

    fun getMoreRestaurants() {
        TODO("api로 장소들 받아서 추가하기")
    }
}
