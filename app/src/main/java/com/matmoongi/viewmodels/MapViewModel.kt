package com.matmoongi.viewmodels

import androidx.lifecycle.ViewModel
import com.matmoongi.data.FavoritesRepository

class MapViewModel(
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {
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
