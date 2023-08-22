package com.matmoongi.search

import com.matmoongi.Destination
import com.matmoongi.data.dataclass.Coordinate
import com.matmoongi.data.dataclass.SearchRestaurant

sealed class SearchUiState {

    abstract val restaurantList: List<SearchRestaurant>

    abstract val currentLocation: Coordinate

    abstract val nextRoute: Destination?

    data class UserLocation(
        override val restaurantList: List<SearchRestaurant> = listOf(),
        override val currentLocation: Coordinate,
        override val nextRoute: Destination? = null,
    ) : SearchUiState()
}
