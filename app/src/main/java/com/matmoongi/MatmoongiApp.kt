package com.matmoongi

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.matmoongi.viewmodels.MyPageViewModel
import com.matmoongi.viewmodels.SearchViewModel
import com.matmoongi.viewmodels.UserViewModel

enum class Destination(val destination: String) {
    LOGIN_SCREEN("LoginScreen"),
    SEARCH_SCREEN("SearchScreen"),
    MY_PAGE_SCREEN("MyPageScreen"),
    FAVORITE_SCREEN("FavoriteScreen"),
    TERMS_SCREEN("TermsScreen"),
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun MatmoongiApp(
    userViewModel: UserViewModel,
    searchViewModel: SearchViewModel,
    myPageViewModel: MyPageViewModel,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colorScheme.primary,
    )
    val navController = rememberNavController()
    AppNavHost(
        navController = navController,
        userViewModel = userViewModel,
        searchViewModel = searchViewModel,
        myPageViewModel = myPageViewModel,
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
private fun AppNavHost(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    userViewModel: UserViewModel,
    myPageViewModel: MyPageViewModel,
) {
    val context = LocalContext.current
    // 자동 로그인
    userViewModel.autoLogin(context, navController::goToSearch)

    NavHost(
        navController = navController,
        startDestination = Destination.LOGIN_SCREEN.destination,
    ) {
        composable(Destination.LOGIN_SCREEN.destination) {
            LoginScreen(
                onClickSkipLoginButton = navController::skipToSearch,
            ) {
                userViewModel.onClickLoginButton(context, navController::goToSearch)
            }
        }

        composable(Destination.SEARCH_SCREEN.destination) {
            SearchScreen(
                searchViewModel.getSearchRestaurantList(),
                navController::goToMyPage,
            )
        }

        composable(Destination.MY_PAGE_SCREEN.destination) {
            MyPageScreen(
                uiState = myPageViewModel.uiState.collectAsState().value,
                emitEvent = myPageViewModel::emitEvent,
                onTapMenuItem = myPageViewModel::onTapMenuItem,
                onPressBack = myPageViewModel::onPressBack,
                onNavigateToSearch = navController::backToSearch,
                onNavigateToLogin = navController::goToLogin,
                onNavigateToFavorite = navController::goToFavorite,
                onNavigateToTerms = navController::goToTerms,
            )
        }

        composable(Destination.FAVORITE_SCREEN.destination) {
            FavoriteScreen()
        }

        composable(Destination.TERMS_SCREEN.destination) {
            TermsScreen()
        }
    }
}

private fun NavController.backToSearch() {
    navigate(Destination.SEARCH_SCREEN.destination) {
        popBackStack()
        launchSingleTop = true
    }
}

private fun NavController.goToMyPage() {
    navigate(Destination.MY_PAGE_SCREEN.destination) {
        launchSingleTop = true
    }
}

private fun NavController.skipToSearch() {
    navigate(Destination.SEARCH_SCREEN.destination) {
        launchSingleTop = true
    }
}

private fun NavController.goToSearch() {
    navigate(Destination.SEARCH_SCREEN.destination) {
        // 로그인 후 서치 화면으로 갈때 백스택 초기화
        popBackStack(
            Destination.LOGIN_SCREEN.destination,
            inclusive = true,
            saveState = false,
        )
        launchSingleTop = true
    }
}

private fun NavController.goToLogin() {
    navigate(Destination.LOGIN_SCREEN.destination) {
        // 로그인 화면으로 갈때는 백스택 초기화
        popBackStack(
            Destination.SEARCH_SCREEN.destination,
            inclusive = true,
            saveState = false,
        )
        launchSingleTop = true
    }
}

private fun NavController.goToFavorite() {
    navigate(Destination.FAVORITE_SCREEN.destination) {
        launchSingleTop = true
    }
}

private fun NavController.goToTerms() {
    navigate(Destination.FAVORITE_SCREEN.destination) {
        launchSingleTop = true
    }
}
