@file:OptIn(ExperimentalMaterial3Api::class)

package com.matmoongi.map

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.matmoongi.R
import com.matmoongi.data.dataclass.Coordinate
import com.matmoongi.data.dataclass.MapRestaurant
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource

@Composable
fun MapScreen(
    mapViewModel: MapViewModel = MapViewModel(),
    restaurant: MapRestaurant,
    userLocation: Coordinate,
) {
    mapViewModel.emitEvent(MapViewEvent.InitMap(restaurant, userLocation))

    ConstraintLayout {
        val (appBar, map, card) = createRefs()

        Map(
            restaurant,
            userLocation,
            Modifier.constrainAs(map) {
                top.linkTo(appBar.bottom)
                bottom.linkTo(card.top)
            },
        )

        TopBar(
            Modifier.constrainAs(appBar) {
                top.linkTo(parent.top)
            },
        )

        SmallRestaurantCard(
            restaurant,
            Modifier.constrainAs(card) {
                bottom.linkTo(parent.bottom)
            },
        )
    }
}

@ExperimentalMaterial3Api
@Composable
private fun TopBar(modifier: Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "경로안내",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.background,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

@Composable
private fun Map(
    restaurant: MapRestaurant,
    userLocation: Coordinate,
    modifier: Modifier,
) {
    val nowPosL = LatLng(userLocation.latitude, userLocation.longitude)
    val destinationL = LatLng(restaurant.coordinate.latitude, restaurant.coordinate.longitude)
    val activity = LocalContext.current as Activity
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            MapView(context)
                .apply {
                    getMapAsync { naverMap ->
                        val locationSource = FusedLocationSource(activity, 1000)
                        naverMap.locationSource = locationSource
                        naverMap.locationTrackingMode = LocationTrackingMode.NoFollow
                        naverMap.locationOverlay.isVisible = true
                        val marker = Marker(
                            LatLng(restaurant.coordinate.latitude, restaurant.coordinate.longitude),
                            OverlayImage.fromResource(R.drawable.marker),
                        )
                        marker.map = naverMap

                        val cameraUpdate = CameraUpdate
                            .fitBounds(
                                LatLngBounds
                                    .from(nowPosL, destinationL),
                                100,
                            ) // 100px의 여백
                        naverMap.moveCamera(cameraUpdate)
                        if (!LatLngBounds(nowPosL, destinationL).contains(destinationL)) {
                            naverMap.moveCamera(
                                CameraUpdate.zoomOut().animate(CameraAnimation.Easing),
                            )
                        }
                    }
                }
        },
    )
}
