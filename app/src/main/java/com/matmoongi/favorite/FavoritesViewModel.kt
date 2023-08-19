package com.matmoongi.favorite

import androidx.lifecycle.ViewModel
import com.matmoongi.data.dataclass.FavoriteRestaurant
import com.matmoongi.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {
    // firebase의 리스트와 동기화 해야하므로 savedStateHandle 사용하지 않음
    private val _favoritesState: MutableStateFlow<List<FavoriteRestaurant>> = MutableStateFlow(
        emptyList(),
    )

    // The UI collects from this StateFlow to get its state updates
    val favoritesState: StateFlow<List<FavoriteRestaurant>> = _favoritesState

    /** 음식점 즐겨찾기 버튼 클릭 **/
    private fun onClickFavoriteButton() {
        TODO("즐겨찾기 등록 상태에 따라 추가/제거 메서드")
    }

    private suspend fun getFavoriteRestaurantsList() {
        favoritesRepository.getFavoriteRestaurants()
        TODO("favoriteRestaurant로 가공해서 _favoritesState에 할당")
    }

    private fun registerToFavorites() {
        favoritesRepository.register()
    }

    private fun unregisterInFavorites() {
        favoritesRepository.unregister()
    }
}
