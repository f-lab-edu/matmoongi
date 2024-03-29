package com.matmoongi

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import com.matmoongi.data.dataclass.Coordinate
import com.matmoongi.data.dataclass.MapRestaurant
import com.matmoongi.favorite.FavoriteScreen
import com.matmoongi.login.LoginScreen
import com.matmoongi.login.LoginViewEvent
import com.matmoongi.login.LoginViewModel
import com.matmoongi.map.MapScreen
import com.matmoongi.mypage.MyPageScreen
import com.matmoongi.mypage.MyPageViewModel
import com.matmoongi.mypage.TermsScreen
import com.matmoongi.search.SearchScreen
import com.matmoongi.search.SearchViewModel

enum class Destination(val destination: String) {
    LOGIN_SCREEN("LoginScreen"),
    SEARCH_SCREEN("SearchScreen"),
    MY_PAGE_SCREEN("MyPageScreen"),
    FAVORITE_SCREEN("FavoriteScreen"),
    TERMS_SCREEN("TermsScreen"),
    MAP_SCREEN("MapScreen"),
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun MatmoongiApp(
    loginViewModel: LoginViewModel,
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
        loginViewModel = loginViewModel,
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
    loginViewModel: LoginViewModel,
    myPageViewModel: MyPageViewModel,
) {
    val context = LocalContext.current
    // 자동 로그인
    loginViewModel.emitEvent(LoginViewEvent.OnAutoLogin(context))

    NavHost(
        navController = navController,
        startDestination = Destination.LOGIN_SCREEN.destination,
    ) {
        composable(Destination.LOGIN_SCREEN.destination) {
            LoginScreen(
                loginViewModel.uiState.collectAsState().value,
                loginViewModel::emitEvent,
                navController::goToSearch,
            )
        }

        composable(Destination.SEARCH_SCREEN.destination) {
            SearchScreen(
                uiState = searchViewModel.uiState.collectAsState().value,
                emitEvent = searchViewModel::emitEvent,
                onNavigateToMyPage = navController::goToMyPage,
                onNavigateToMap = navController::goToMap,
            )
        }

        composable(Destination.MY_PAGE_SCREEN.destination) {
            MyPageScreen(
                uiState = myPageViewModel.uiState.collectAsState().value,
                emitEvent = myPageViewModel::emitEvent,
                onNavigateToSearch = navController::backToSearch,
                onNavigateToLogin = navController::goToLogin,
                onNavigateToFavorite = navController::goToFavorite,
                onNavigateToTerms = navController::goToTerms,
            )
        }

        composable(
            route = Destination.MAP_SCREEN.destination + "/{restaurant}/{userLocation}",
            arguments = listOf(
                navArgument("restaurant") { NavType.StringType },
                navArgument("userLocation") { NavType.StringType },
            ),
        ) { backStackEntry ->
            val restaurant = Gson().fromJson(
                backStackEntry.arguments?.getString("restaurant"),
                MapRestaurant::class.java,
            )
            val userLocation = Gson().fromJson(
                backStackEntry.arguments?.getString("userLocation"),
                Coordinate::class.java,
            )
            MapScreen(
                restaurant = restaurant,
                userLocation = userLocation,
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

private fun NavController.goToSearch() {
    navigate(Destination.SEARCH_SCREEN.destination) {
        popBackStack(
            Destination.LOGIN_SCREEN.destination,
            inclusive = true,
            saveState = false,
        )
        launchSingleTop = true
    }
}

private fun NavController.goToMap(restaurant: MapRestaurant, userLocation: Coordinate) {
    val restaurantJson = Gson().toJson(restaurant)
    val userLocationJson = Gson().toJson(userLocation)
    navigate(Destination.MAP_SCREEN.destination + "/$restaurantJson/$userLocationJson")
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
