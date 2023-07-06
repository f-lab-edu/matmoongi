package com.matmoongi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import com.matmoongi.screens.SplashScreen
import com.matmoongi.viewmodels.FavoritesViewModel
import com.matmoongi.viewmodels.SearchViewModel

class MainActivity : ComponentActivity() {
    val searchViewModel: SearchViewModel by viewModels()
    val favoritesViewModel: FavoritesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme { SplashScreen() }
        }
    }
}
