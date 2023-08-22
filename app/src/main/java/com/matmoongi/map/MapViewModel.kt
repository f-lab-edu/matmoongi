package com.matmoongi.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matmoongi.data.dataclass.Coordinate
import com.matmoongi.data.dataclass.MapRestaurant
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel() : ViewModel() {
    val uiState: StateFlow<MapUiState>
        get() = _uiState.asStateFlow()

    private val _uiState = MutableStateFlow<MapUiState>(
        MapUiState.Empty,
    )

    private val eventChannel: SendChannel<MapViewEvent>
        get() = _eventChannel

    private val _eventChannel = Channel<MapViewEvent>()

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

    fun emitEvent(mapViewEvent: MapViewEvent) {
        viewModelScope.launch { eventChannel.send(mapViewEvent) }
    }

    private fun onReceiveEvent(event: MapViewEvent) {
        when (event) {
            is MapViewEvent.InitMap -> onInitMap(event.restaurant, event.userLocation)
        }
    }

    private fun onInitMap(restaurant: MapRestaurant, userLocation: Coordinate) {
        when (_uiState.value) {
            MapUiState.Empty -> {
                _uiState.value = MapUiState.WithData(restaurant, userLocation)
            }
            else -> Unit
        }
    }
}
