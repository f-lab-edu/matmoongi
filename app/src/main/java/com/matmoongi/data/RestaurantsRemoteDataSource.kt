package com.matmoongi.data

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.matmoongi.BuildConfig
import com.matmoongi.LocationResultCallback
import com.matmoongi.network.GoogleMapAPIService
import com.matmoongi.response.Place
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Locale

private const val LAT_LNG_PARAMETER_FORMAT = "%.7f,%.7f"

class RestaurantsRemoteDataSource(
    private val googleMapAPIService: GoogleMapAPIService,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val ioDispatcher: CoroutineDispatcher,
) {
    /** 앱 권한 미동의 시 호출 불가 -> MainActivity에서 처리중 */
    @SuppressLint("MissingPermission")
    fun fetchCurrentLatLng(locationResultCallback: LocationResultCallback) {
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val location = task.result
                    val coordinate = Coordinate(location.latitude, location.longitude)
                    locationResultCallback.onLocationResult(coordinate)
                } else {
                    locationResultCallback.onError(task.exception ?: Exception("Unknown error"))
                }
            }
    }

    suspend fun fetchNearbyRestaurants(coordinate: Coordinate): List<Place> =
        withContext(ioDispatcher) {
            val location =
                String.format(Locale.ROOT, LAT_LNG_PARAMETER_FORMAT, coordinate.lat, coordinate.lng)
            googleMapAPIService.fetchNearbyRestaurants(
                location = location,
                apiKey = BuildConfig.GOOGLE_PLACE_API_KEY,
            ).results
        }

    fun getPhotoRequestUrl(
        photoReference: String,
        maxWidth: Int,
        maxHeight: Int,
        googleApiKey: String,
    ) =
        GoogleMapAPIService.getPhotoRequestUrl(photoReference, maxWidth, maxHeight, googleApiKey)
}
