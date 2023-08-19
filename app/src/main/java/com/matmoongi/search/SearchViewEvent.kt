package com.matmoongi.search

import com.matmoongi.Destination
import com.matmoongi.data.dataclass.SearchRestaurant

sealed class SearchViewEvent {

    object OnTapRefreshUserLocationButton : SearchViewEvent()

    data class OnTapRestaurantCard(val searchRestaurant: SearchRestaurant) : SearchViewEvent()

    object OnUserLocationChanged : SearchViewEvent()

    object OnTapUserIcon : SearchViewEvent()

    data class OnNavigateTo(val destination: Destination) : SearchViewEvent()
}
