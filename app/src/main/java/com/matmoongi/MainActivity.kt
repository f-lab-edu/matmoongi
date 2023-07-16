package com.matmoongi

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.matmoongi.theme.MatmoongiTheme
import com.matmoongi.viewmodels.FavoritesViewModel
import com.matmoongi.viewmodels.SearchViewModel

private const val COARSE_LOCATION_PERMISSION = android.Manifest.permission.ACCESS_COARSE_LOCATION
private const val FINE_LOCATION_PERMISSION = android.Manifest.permission.ACCESS_FINE_LOCATION

@ExperimentalMaterial3Api
class MainActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModels()
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermission(this)

        setContent {
            MatmoongiTheme { MatmoongiApp(searchViewModel) }
        }
    }
}

fun showPermissionGuideDialog(activity: AppCompatActivity) {
    val fragmentManager = activity.supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction
        .add(android.R.id.content, PermissionDialogFragment())
        .addToBackStack(null)
        .commit()
}

private fun checkPermission(activity: AppCompatActivity) {
    val locationPermissionRequest = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissions ->
        when {
            permissions.getOrDefault(FINE_LOCATION_PERMISSION, false) -> {
            }
            permissions.getOrDefault(COARSE_LOCATION_PERMISSION, false) -> {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.toast_need_fine_location_permission),
                    Toast.LENGTH_SHORT,
                )
                    .show()
            }
            else -> {
                Toast.makeText(
                    activity,
                    activity.getString(R.string.toast_need_location_permission),
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    when {
        ContextCompat.checkSelfPermission(
            activity,
            COARSE_LOCATION_PERMISSION,
        ) == PackageManager.PERMISSION_GRANTED -> {
        }

        shouldShowRequestPermissionRationale(activity, COARSE_LOCATION_PERMISSION)
        -> {
            showPermissionGuideDialog(activity)
        }

        else -> {
            locationPermissionRequest.launch(
                arrayOf(COARSE_LOCATION_PERMISSION, FINE_LOCATION_PERMISSION),
            )
        }
    }
}
