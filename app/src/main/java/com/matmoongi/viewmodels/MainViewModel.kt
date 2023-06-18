package com.matmoongi.viewmodels

import androidx.lifecycle.ViewModel
import com.matmoongi.data.FavoritesRepository
import com.matmoongi.data.RestaurantsRepository

class MainViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val restaurantsRepository: RestaurantsRepository,
) : ViewModel() {
    init {
        searchRestaurants()
    }

    private fun searchRestaurants() {}

    private fun moreRestaurants() {}

    private fun registerToFavorites() {}

    private fun goToMap() {}

    private fun goToUserMenu() {}
}
