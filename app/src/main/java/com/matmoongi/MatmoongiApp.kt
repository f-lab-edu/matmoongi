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
import com.matmoongi.viewmodels.UserViewModel

private const val LOGIN_SCREEN = "loginScreen"
private const val SEARCH_SCREEN = "searchScreen"
private const val MY_PAGE_SCREEN = "myPageScreen"
private const val FAVORITE_SCREEN = "favoriteScreen"
private const val TERMS_SCREEN = "termsScreen"

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun MatmoongiApp(userViewModel: UserViewModel, searchViewModel: SearchViewModel) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colorScheme.primary,
    )
    val navController = rememberNavController()
    AppNavHost(
        navController = navController,
        userViewModel = userViewModel,
        searchViewModel = searchViewModel,
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
private fun AppNavHost(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    userViewModel: UserViewModel,
) {
    val context = LocalContext.current
    val onClickLogoutButton = userViewModel.onClickLogoutButton { navController.goToLogin() }
    val onClickSignOutButton = userViewModel.onClickSignOutButton { navController.goToLogin() }
    // 자동 로그인
    userViewModel.autoLogin(context, navController::goToSearch)

    NavHost(
        navController = navController,
        startDestination = LOGIN_SCREEN,
    ) {
        composable(LOGIN_SCREEN) {
            LoginScreen(
                onClickSkipLoginButton = navController::skipToSearch,
            ) {
                userViewModel.onClickLoginButton(context, navController::goToSearch)
            }
        }

        composable(SEARCH_SCREEN) {
            SearchScreen(
                searchViewModel.getSearchRestaurantList(),
                navController::goToMyPage,
            )
        }

        composable(MY_PAGE_SCREEN) {
            MyPageScreen(
                searchViewModel.getMyPageItemList(),
                navController::backToSearch,
                searchViewModel.onClickMyPageMenuItem(
                    onClickLoginItem = { navController.goToLogin() },
                    onClickLogoutItem = onClickLogoutButton,
                    onClickFavoriteItem = { navController.goToFavorite() },
                    onClickTermsItem = { navController.goToTerms() },
                    onClickSignOutItem = onClickSignOutButton,
                ),
            )
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

private fun NavController.skipToSearch() {
    navigate(SEARCH_SCREEN) {
        launchSingleTop = true
    }
}

private fun NavController.goToSearch() {
    navigate(SEARCH_SCREEN) {
        // 로그인 후 서치 화면으로 갈때 백스택 초기화
        popBackStack(
            LOGIN_SCREEN,
            inclusive = true,
            saveState = false,
        )
        launchSingleTop = true
    }
}

private fun NavController.goToLogin() {
    navigate(LOGIN_SCREEN) {
        // 로그인 화면으로 갈때는 백스택 초기화
        popBackStack(
            SEARCH_SCREEN,
            inclusive = true,
            saveState = false,
        )
        launchSingleTop = true
    }
}

private fun NavController.goToFavorite() {
    navigate(FAVORITE_SCREEN) {
        launchSingleTop = true
    }
}

private fun NavController.goToTerms() {
    navigate(FAVORITE_SCREEN) {
        launchSingleTop = true
    }
}
