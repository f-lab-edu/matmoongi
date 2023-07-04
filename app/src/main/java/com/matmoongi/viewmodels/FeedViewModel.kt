package com.matmoongi.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matmoongi.data.FavoritesRepository
import com.matmoongi.data.FeedRestaurant
import com.matmoongi.data.Restaurant
import com.matmoongi.data.RestaurantsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val FEED_RESTAURANTS_LIST = "feedRestaurantsList"

class FeedViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val restaurantsRepository: RestaurantsRepository,
    private val state: SavedStateHandle,
) : ViewModel() {

    private val restaurantsState: StateFlow<List<FeedRestaurant>> = state.getStateFlow(
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
            withContext(Dispatchers.IO) { restaurantsRepository.getNearbyRestaurants() }
        state[FEED_RESTAURANTS_LIST] = restaurants.map { it.toMainRestaurant() }
    }

    private fun onClickSearchButton() {
        viewModelScope.launch { refreshNearbyRestaurantList() }
    }

    private fun Restaurant.toMainRestaurant(): FeedRestaurant = TODO("...")

    /** 음식점 리스트 마지막 아이템을 오른쪽으로 당겨서 추가 목록 받기 **/
    private suspend fun onPullLastItem() {
        val currentRestaurants: List<FeedRestaurant> = state.get<List<FeedRestaurant>>(
            FEED_RESTAURANTS_LIST,
        ).orEmpty()
        val restaurants =
            withContext(Dispatchers.IO) { restaurantsRepository.getNearbyRestaurants() }
        state[FEED_RESTAURANTS_LIST] = currentRestaurants + restaurants.map {
            it.toMainRestaurant()
        }
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
