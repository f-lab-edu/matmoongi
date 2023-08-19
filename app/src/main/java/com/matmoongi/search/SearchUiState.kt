package com.matmoongi.search

import com.matmoongi.Destination
import com.matmoongi.data.dataclass.Coordinate
import com.matmoongi.data.dataclass.MapRestaurant
import com.matmoongi.data.dataclass.SearchRestaurant

sealed class SearchUiState {

    abstract val restaurantList: List<SearchRestaurant>

    abstract val userLocation: Coordinate

    abstract val restaurant: SearchRestaurant?

    abstract val mapRestaurant: MapRestaurant?

    abstract val nextRoute: Destination?

    data class Default(
        override val restaurantList: List<SearchRestaurant> = listOf(),
        override val userLocation: Coordinate,
        override val restaurant: SearchRestaurant? = null,
        override val mapRestaurant: MapRestaurant? = null,
        override val nextRoute: Destination? = null,
    ) : SearchUiState()
}
