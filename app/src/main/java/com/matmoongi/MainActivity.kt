package com.matmoongi

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.matmoongi.favorite.FavoritesViewModel
import com.matmoongi.login.LoginViewModel
import com.matmoongi.mypage.MyPageViewModel
import com.matmoongi.search.SearchViewModel
import com.matmoongi.theme.MatmoongiTheme
import org.greenrobot.eventbus.EventBus

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
class MainActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModel.provideFactory(this)
    }
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels { LoginViewModel.Factory }
    private val myPageViewModel: MyPageViewModel by viewModels { MyPageViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermission(this)

        EventBus.builder().installDefaultEventBus()

        searchViewModel.currentLocationState.observe(this) {
            searchViewModel.fetchNearbyRestaurantList()
        }

        setContent {
            MatmoongiTheme { MatmoongiApp(loginViewModel, searchViewModel, myPageViewModel) }
        }
    }
}

private fun showPermissionGuideDialog(activity: AppCompatActivity) {
    val fragmentManager = activity.supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.add(android.R.id.content, PermissionDialogFragment())
        .addToBackStack(null)
        .commit()
}

private fun checkPermission(activity: AppCompatActivity) {
    val locationPermissionRequest = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
        explainRationalToNeedFineGrainedLocationPermission(),
    )

    when {
        activity.isGranted(ACCESS_COARSE_LOCATION) -> {}

        shouldShowRequestPermissionRationale(activity, ACCESS_COARSE_LOCATION)
        -> {
            showPermissionGuideDialog(activity)
        }

        else -> {
            locationPermissionRequest.launch(
                arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION),
            )
        }
    }
}

private fun explainRationalToNeedFineGrainedLocationPermission() =
    ActivityResultCallback<Map<String, @JvmSuppressWildcards Boolean>> {
        if (it[ACCESS_FINE_LOCATION] == false) {
            TODO("SnackBar 구현")
        }
        if (it[ACCESS_COARSE_LOCATION] == false) {
            TODO("SnackBar 구현")
        }
    }

private fun Context.isGranted(permission: String): Boolean {
    val checkResult = ContextCompat.checkSelfPermission(this, permission)
    return checkResult == PackageManager.PERMISSION_GRANTED
}
