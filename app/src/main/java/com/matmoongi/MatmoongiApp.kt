package com.matmoongi

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.matmoongi.screens.LoginScreen
import com.matmoongi.screens.MyPageScreen
import com.matmoongi.screens.SearchScreen
import com.matmoongi.viewmodels.SearchViewModel

private const val LOGIN_SCREEN = "loginScreen"
private const val SEARCH_SCREEN = "searchScreen"
private const val MY_PAGE_SCREEN = "myPageScreen"
private const val FAVORITE_SCREEN = "favoriteScreen"
private const val TERMS_SCREEN = "termsScreen"

@ExperimentalMaterial3Api
@Composable
fun MatmoongiApp(searchViewModel: SearchViewModel) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colorScheme.primary,
    )
    val navController = rememberNavController()
    AppNavHost(
        navController = navController,
        searchViewModel = searchViewModel,
    )
}

@ExperimentalMaterial3Api
@Composable
private fun AppNavHost(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = LOGIN_SCREEN,
    ) {
        composable(LOGIN_SCREEN) {
            LoginScreen(navController::skipLoginToSearch)
        }

        composable(SEARCH_SCREEN) {
            SearchScreen(
                searchViewModel.restaurantsState.collectAsState().value,
                navController::goToMyPage,
            )
        }

        composable(MY_PAGE_SCREEN) {
            MyPageScreen()
        }

        composable(FAVORITE_SCREEN) {
            MyPageScreen()
        }

        composable(TERMS_SCREEN) {
            MyPageScreen()
        }
    }
}

private fun NavController.goToMyPage() {
    navigate(MY_PAGE_SCREEN)
}
private fun NavController.skipLoginToSearch() {
    navigate(SEARCH_SCREEN)
}
