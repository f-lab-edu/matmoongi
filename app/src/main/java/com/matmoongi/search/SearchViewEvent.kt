package com.matmoongi.search

import com.matmoongi.Destination

sealed class SearchViewEvent {

    object OnTapRefreshUserLocationButton : SearchViewEvent()

    object OnUserLocationChanged : SearchViewEvent()

    object OnTapUserIcon : SearchViewEvent()

    data class OnNavigateTo(val destination: Destination) : SearchViewEvent()
}
