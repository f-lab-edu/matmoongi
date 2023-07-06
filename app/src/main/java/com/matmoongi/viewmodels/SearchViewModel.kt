package com.matmoongi.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matmoongi.data.FavoritesRepository
import com.matmoongi.data.Restaurant
import com.matmoongi.data.RestaurantsRepository
import com.matmoongi.data.SearchRestaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val FEED_RESTAURANTS_LIST = "feedRestaurantsList"

class SearchViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val restaurantsRepository: RestaurantsRepository,
    private val state: SavedStateHandle,
) : ViewModel() {

    private val restaurantsState: StateFlow<List<SearchRestaurant>> = state.getStateFlow(
        FEED_RESTAURANTS_LIST,
        emptyList(),
    )

    init {
        // 앱 시작 시 현 위치로 음식점 검색
        viewModelScope.launch { refreshNearbyRestaurantList() }
    }

    /** 주변 음식점을 검색해서 받아오기 - 현재 위치 기반 **/
    private suspend fun refreshNearbyRestaurantList() {
        val restaurants =
            withContext(Dispatchers.IO) {
                restaurantsRepository.getNearbyRestaurants()
            }
//        즐겨찾기된 음식점 placeId의 Set (isLike 확인을 위해)
        val favoriteRestaurantPlaceIds = withContext(Dispatchers.IO) {
            favoritesRepository.getFavoriteRestaurants().map { it.placeId }.toSet()
        }
        state[FEED_RESTAURANTS_LIST] = restaurants.map {
            it.toSearchRestaurant(favoriteRestaurantPlaceIds.contains(it.placeId))
        }
    }

    private suspend fun getMoreNearbyRestaurantList() {
        val currentRestaurants: List<SearchRestaurant> = state.get<List<SearchRestaurant>>(
            FEED_RESTAURANTS_LIST,
        ).orEmpty()
        val restaurants =
            withContext(Dispatchers.IO) { restaurantsRepository.getNearbyRestaurants() }
//        즐겨찾기된 음식점 placeId의 Set (isLike 확인을 위해)
        val favoriteRestaurantPlaceIds = withContext(Dispatchers.IO) {
            favoritesRepository.getFavoriteRestaurants().map { it.placeId }.toSet()
        }
        state[FEED_RESTAURANTS_LIST] = currentRestaurants + restaurants.map {
            it.toSearchRestaurant(favoriteRestaurantPlaceIds.contains(it.placeId))
        }
    }

    private fun onClickSearchButton() {
        viewModelScope.launch { refreshNearbyRestaurantList() }
    }

    private fun Restaurant.toSearchRestaurant(isLike: Boolean): SearchRestaurant = TODO("...")

    /** 음식점 리스트 마지막 아이템을 오른쪽으로 당겨서 추가 목록 받기 **/
    private suspend fun onPullLastItem() {
        viewModelScope.launch { getMoreNearbyRestaurantList() }
    }

    /** 음식점 즐겨찾기 버튼 클릭 **/
    private fun onClickFavoriteButton() {
        TODO("즐겨찾기 등록 상태에 따라 추가/제거 메서드")
    }

    private fun registerToFavorites() {
        favoritesRepository.register()
    }

    private fun unregisterInFavorites() {
        favoritesRepository.unregister()
    }
}
