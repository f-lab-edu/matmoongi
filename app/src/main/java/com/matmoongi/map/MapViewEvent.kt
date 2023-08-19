package com.matmoongi.map

import com.matmoongi.data.dataclass.Coordinate
import com.matmoongi.data.dataclass.MapRestaurant

sealed class MapViewEvent {
    data class InitMap(val restaurant: MapRestaurant, val userLocation: Coordinate) : MapViewEvent()
}
