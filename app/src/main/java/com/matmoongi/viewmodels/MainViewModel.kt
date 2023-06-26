package com.matmoongi.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.matmoongi.data.FavoritesRepository
import com.matmoongi.data.MainRestaurant
import com.matmoongi.data.RestaurantsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val restaurantsRepository: RestaurantsRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    private val restaurantsState: StateFlow<List<MainRestaurant>> = state.getStateFlow("restaurantsState", emptyList())

    init {
        searchRestaurants()
    }

    /** 주변 음식점을 검색해서 받아오기 - 시작할 때와 현재 위치에서 재검색 버튼 클릭 **/
    private fun searchRestaurants() {}

    /** 음식점 리스트 마지막 아이템을 오른쪽으로 당겨서 추가 목록 받기 **/
    private fun moreRestaurants() {}

    /** 음식점 즐겨찾기 버튼 클릭 - 추가 **/
    private fun registerToFavorites() {}

    /** 음식점 즐겨찾기 버튼 클릭 - 제거 **/
    private fun unregisterInFavorites() {}
}
