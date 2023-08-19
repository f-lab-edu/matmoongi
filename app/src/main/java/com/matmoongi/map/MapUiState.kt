package com.matmoongi.map

import com.matmoongi.data.dataclass.Coordinate
import com.matmoongi.data.dataclass.MapRestaurant

sealed class MapUiState {
    abstract val restaurant: MapRestaurant?
    abstract val userLocation: Coordinate?

    data class WithData(
        override val restaurant: MapRestaurant,
        override val userLocation: Coordinate,
    ) : MapUiState()

    object Empty : MapUiState() {
        override val restaurant: MapRestaurant? = null
        override val userLocation: Coordinate? = null
    }
}
