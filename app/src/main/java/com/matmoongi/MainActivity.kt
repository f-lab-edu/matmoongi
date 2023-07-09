package com.matmoongi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import com.matmoongi.screens.LoginScreen
import com.matmoongi.theme.MatmoongiTheme
import com.matmoongi.viewmodels.FavoritesViewModel
import com.matmoongi.viewmodels.SearchViewModel

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    private val searchViewModel: SearchViewModel by viewModels()
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MatmoongiTheme { LoginScreen() }
        }
    }
}
