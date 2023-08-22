package com.matmoongi.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.gms.location.LocationServices
import com.matmoongi.Destination
import com.matmoongi.data.dataclass.Coordinate
import com.matmoongi.data.datasource.RestaurantsRemoteDataSource
import com.matmoongi.data.repository.RestaurantsRepository
import com.matmoongi.network.GoogleMapAPIService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val DEFAULT_LATITUDE: Double = 37.5666103
private const val DEFAULT_LONGITUDE: Double = 126.9783882

class SearchViewModel(
    private val restaurantsRepository: RestaurantsRepository,
) : ViewModel() {

    val uiState: StateFlow<SearchUiState>
        get() = _uiState.asStateFlow()

    private val _uiState = MutableStateFlow<SearchUiState>(
        SearchUiState.UserLocation(
            currentLocation = Coordinate(DEFAULT_LATITUDE, DEFAULT_LONGITUDE),
        ),
    )

    private val eventChannel: SendChannel<SearchViewEvent>
        get() = _eventChannel

    private val _eventChannel = Channel<SearchViewEvent>()

    init {
        viewModelScope.launch {
            for (event in _eventChannel) {
                onReceiveEvent(event)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _eventChannel.close()
    }

    fun emitEvent(searchViewEvent: SearchViewEvent) {
        viewModelScope.launch { eventChannel.send(searchViewEvent) }
    }

    private fun onReceiveEvent(event: SearchViewEvent) {
        when (event) {
            is SearchViewEvent.OnTapRefreshUserLocationButton -> onRefreshUserLocation()
            is SearchViewEvent.OnUserLocationChanged -> onUserLocationChanged()
            is SearchViewEvent.OnTapUserIcon -> onTapUserIcon()
            is SearchViewEvent.OnNavigateTo -> onNavigateTo(event.destination)
        }
    }

    private fun onRefreshUserLocation() {
        viewModelScope.launch {
            val currentState = _uiState.value
            val result: Result<Coordinate> = restaurantsRepository.fetchCurrentLocation()

            when (currentState) {
                is SearchUiState.UserLocation -> {
                    result.fold(
                        onSuccess = { coordinate ->
                            _uiState.value = currentState.copy(currentLocation = coordinate)
                        },
                        onFailure = { TODO("실패 메세지 스낵바 or 토스트메세지") },
                    )
                }
            }
        }
    }

    private fun onUserLocationChanged() {
        onFetchNearbyRestaurants()
    }

    private fun onFetchNearbyRestaurants() {
        viewModelScope.launch {
            when (val currentState = _uiState.value) {
                is SearchUiState.UserLocation -> {
                    val coordinate = currentState.currentLocation
                    val restaurantList = restaurantsRepository.fetchNearbyRestaurant(coordinate)
                    _uiState.value = currentState.copy(restaurantList = restaurantList)
                }
            }
        }
    }

    private fun onTapUserIcon() {
        when (val currentState = _uiState.value) {
            is SearchUiState.UserLocation -> {
                _uiState.value = currentState.copy(nextRoute = Destination.MY_PAGE_SCREEN)
            }
        }
    }

    private fun onNavigateTo(destination: Destination) {
        when (val currentState = _uiState.value) {
            is SearchUiState.UserLocation -> {
                if (currentState.nextRoute == destination) {
                    _uiState.value = currentState.copy(nextRoute = null)
                }
            }
        }
    }

    companion object {
        fun provideFactory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(
                    RestaurantsRepository(
                        RestaurantsRemoteDataSource(
                            GoogleMapAPIService.getService(),
                            LocationServices.getFusedLocationProviderClient(context),
                        ),
                    ),
                )
            }
        }
    }
}
