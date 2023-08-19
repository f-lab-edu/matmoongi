package com.matmoongi.search

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.gms.location.LocationServices
import com.matmoongi.data.dataclass.Coordinate
import com.matmoongi.data.dataclass.SearchRestaurant
import com.matmoongi.data.datasource.RestaurantsRemoteDataSource
import com.matmoongi.data.repository.RestaurantsRepository
import com.matmoongi.network.GoogleMapAPIService
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val DEFAULT_LATITUDE: Double = 37.5666103
private const val DEFAULT_LONGITUDE: Double = 126.9783882
private const val SEARCH_RESTAURANT_STATE = "searchRestaurantList"

class SearchViewModel(
//    private val favoritesRepository: FavoritesRepository,
    private val restaurantsRepository: RestaurantsRepository,
    private val state: SavedStateHandle,
) : ViewModel() {

    private val restaurantsState: StateFlow<List<SearchRestaurant>> = state.getStateFlow(
        SEARCH_RESTAURANT_STATE,
        emptyList(),
    )

    val currentLocationState: MutableLiveData<Coordinate> = MutableLiveData<Coordinate>(
        Coordinate(DEFAULT_LATITUDE, DEFAULT_LONGITUDE),
    )

    init {
        // 앱 시작 시 현 위치로 음식점 검색
//        viewModelScope.launch { fetchNearbyRestaurantList() }
    }

    fun refreshCurrentLocation() {
        viewModelScope.launch {
            val result: Result<Coordinate> = restaurantsRepository.fetchCurrentLocation()

            result.fold(
                onSuccess = {
                    currentLocationState.value =
                        result.getOrDefault(Coordinate(DEFAULT_LATITUDE, DEFAULT_LONGITUDE))
                },
                onFailure = { TODO("실패 메세지 스낵바 or 토스트메세지") },
            )
        }
    }

    @Composable
    fun getSearchRestaurantList(): List<SearchRestaurant> = restaurantsState.collectAsState().value

    fun fetchNearbyRestaurantList() {
        this.viewModelScope.launch {
            val restaurants =
                currentLocationState.value?.let { coordinate ->
                    restaurantsRepository.fetchNearbyRestaurant(
                        coordinate,
                    )
                }.orEmpty()
            state[SEARCH_RESTAURANT_STATE] = restaurants
        }
        //        즐겨찾기된 음식점 placeId의 Set (isLike 확인을 위해)
//        val favoriteRestaurantPlaceIds = withContext(Dispatchers.IO) {
//            favoritesRepository.getFavoriteRestaurants().map { it.placeId }.toSet()
//        }
//        state[SEARCH_RESTAURANT_STATE] = restaurants.map {
//            it.toSearchRestaurant(favoriteRestaurantPlaceIds.contains(it.placeId))
//        }
    }

//    private suspend fun getMoreNearbyRestaurantList() {
//        val currentRestaurants: List<SearchRestaurant> = state.get<List<SearchRestaurant>>(
//            FEED_RESTAURANTS_LIST,
//        ).orEmpty()
//        val restaurants =
//            withContext(Dispatchers.IO) { restaurantsRepository.getNearbyRestaurants() }
// //        즐겨찾기된 음식점 placeId의 Set (isLike 확인을 위해)
//        val favoriteRestaurantPlaceIds = withContext(Dispatchers.IO) {
//            favoritesRepository.getFavoriteRestaurants().map { it.placeId }.toSet()
//        }
//        state[FEED_RESTAURANTS_LIST] = currentRestaurants + restaurants.map {
//            it.toSearchRestaurant(favoriteRestaurantPlaceIds.contains(it.placeId))
//        }
//    }
//
//    private fun onClickSearchButton() {
//        viewModelScope.launch { refreshNearbyRestaurantList() }
//    }
//
//    private fun Restaurant.toSearchRestaurant(isLike: Boolean): SearchRestaurant = TODO("...")
//
//    /** 음식점 리스트 마지막 아이템을 오른쪽으로 당겨서 추가 목록 받기 **/
//    private suspend fun onPullLastItem() {
//        viewModelScope.launch { getMoreNearbyRestaurantList() }
//    }
//
//    /** 음식점 즐겨찾기 버튼 클릭 **/
//    private fun onClickFavoriteButton() {
//        TODO("즐겨찾기 등록 상태에 따라 추가/제거 메서드")
//    }
//
//    private fun registerToFavorites() {
//        favoritesRepository.register()
//    }
//
//    private fun unregisterInFavorites() {
//        favoritesRepository.unregister()
//    }

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
                    createSavedStateHandle(),
                )
            }
        }
    }
}
