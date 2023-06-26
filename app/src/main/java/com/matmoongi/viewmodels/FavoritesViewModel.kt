package com.matmoongi.viewmodels

import androidx.lifecycle.ViewModel
import com.matmoongi.data.FavoriteRestaurant
import com.matmoongi.data.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {
    //firebase의 리스트와 동기화 해야하므로 savedStateHandle 사용하지 않음
    private val _favoritesState = MutableStateFlow(toFavoriteRestaurant())
    // The UI collects from this StateFlow to get its state updates
    val favoritesState: StateFlow<List<FavoriteRestaurant>> = _favoritesState

    /** 음식점 즐겨찾기 버튼 클릭 - 제거 **/
    private fun unregister() {
        TODO("상태에 반영하려면 repo를 구독하는 무언가를 만들어야함")
    }

    private fun toFavoriteRestaurant(): List<FavoriteRestaurant> {
        TODO("favorite repo에서 데이터 가져와서 가공하기")
    }
}
