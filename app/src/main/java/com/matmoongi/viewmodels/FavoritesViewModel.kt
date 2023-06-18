package com.matmoongi.viewmodels

import androidx.lifecycle.ViewModel
import com.matmoongi.data.FavoritesRepository

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    private fun unregister() {}

    private fun goToMap() {}

    private fun goBack() {}

    private fun goToMain() {}
}
