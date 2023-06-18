package com.matmoongi.viewmodels

import androidx.lifecycle.ViewModel
import com.matmoongi.data.FavoritesRepository

class MapViewModel(
    private val favoritesRepository: FavoritesRepository
    ) : ViewModel() {
    private fun registerToFavorites() {}

    private fun unregisterInFavorites() {}

    private fun goBack() {}

    private fun goToMain() {}
}
