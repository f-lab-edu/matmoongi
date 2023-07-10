package com.matmoongi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import com.matmoongi.screens.SearchScreen
import com.matmoongi.theme.MatmoongiTheme
import com.matmoongi.viewmodels.FavoritesViewModel
import com.matmoongi.viewmodels.SearchViewModel

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    private val searchViewModel: SearchViewModel by viewModels()
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        TODO("UI 확인을 위해 임시로 SearchScreen() 삽입, LoginScreen으로 바꿀 예정")
        setContent {
            MatmoongiTheme { SearchScreen(searchViewModel.restaurantsState.collectAsState().value) }
        }
    }
}
