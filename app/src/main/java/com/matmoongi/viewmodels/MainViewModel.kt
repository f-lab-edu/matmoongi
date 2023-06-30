package com.matmoongi.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.matmoongi.data.FavoritesRepository
import com.matmoongi.data.MainRestaurant
import com.matmoongi.data.RestaurantsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val restaurantsRepository: RestaurantsRepository,
    private val state: SavedStateHandle,
) : ViewModel() {

    private val restaurantsState: StateFlow<List<MainRestaurant>> = state.getStateFlow(
        "restaurantsState",
        emptyList(),
    )

    init {
        // 앱 시작 시 현 위치로 음식점 검색
        CoroutineScope(Dispatchers.IO).launch { state["restaurantsState"] = onClickSearchButton() }
    }

    /** 주변 음식점을 검색해서 받아오기 - 현재 위치 기반 **/
    private suspend fun onClickSearchButton(): List<MainRestaurant> {
        restaurantsRepository.getNearbyRestaurants()
        TODO("MainRestaurant로 가공해서 리턴")
    }

    /** 음식점 리스트 마지막 아이템을 오른쪽으로 당겨서 추가 목록 받기 **/
    private fun onPullLastItem() {}

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
