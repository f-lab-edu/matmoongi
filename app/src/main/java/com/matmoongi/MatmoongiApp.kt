package com.matmoongi

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.matmoongi.screens.FavoriteScreen
import com.matmoongi.screens.LoginScreen
import com.matmoongi.screens.MyPageScreen
import com.matmoongi.screens.SearchScreen
import com.matmoongi.screens.TermsScreen
import com.matmoongi.viewmodels.SearchViewModel

private const val LOGIN_SCREEN = "loginScreen"
private const val SEARCH_SCREEN = "searchScreen"
private const val MY_PAGE_SCREEN = "myPageScreen"
private const val FAVORITE_SCREEN = "favoriteScreen"
private const val TERMS_SCREEN = "termsScreen"

@ExperimentalFoundationApi
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

@ExperimentalFoundationApi
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
        val oAuthCallback = searchViewModel.oAuthLoginCallback { navController.goToSearch() }

        composable(LOGIN_SCREEN) {
            LoginScreen(
                navController::goToSearch,
                searchViewModel.onClickNaverLoginButton(LocalContext.current, oAuthCallback),
            )
        }

        composable(SEARCH_SCREEN) {
            SearchScreen(
                searchViewModel.getSearchRestaurantList(),
                navController::goToMyPage,
            )
        }

        composable(MY_PAGE_SCREEN) {
            MyPageScreen(navController::backToSearch, searchViewModel.getMyPageItemList())
        }

        composable(FAVORITE_SCREEN) {
            FavoriteScreen()
        }

        composable(TERMS_SCREEN) {
            TermsScreen()
        }
    }
}

private fun NavController.backToSearch() {
    navigate(SEARCH_SCREEN) {
        popBackStack()
        launchSingleTop = true
    }
}

private fun NavController.goToMyPage() {
    navigate(MY_PAGE_SCREEN) {
        launchSingleTop = true
    }
}

private fun NavController.goToSearch() {
    navigate(SEARCH_SCREEN) {
        launchSingleTop = true
    }
}
