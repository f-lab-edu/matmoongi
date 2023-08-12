@file:OptIn(ExperimentalCoroutinesApi::class)

package com.matmoongi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.matmoongi.Destination
import com.matmoongi.MyPageUiState
import com.matmoongi.MyPageViewEvent
import com.matmoongi.data.UserDataSource
import com.matmoongi.data.UserRepository
import com.matmoongi.network.NaverLoginService
import com.matmoongi.network.NaverUserService
import com.navercorp.nid.oauth.NidOAuthLoginState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class MyPageMenu {
    Login, Logout, Favorite, Version, Terms, SignOut
}

class MyPageViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    val uiState: StateFlow<MyPageUiState>
        get() = _uiState.asStateFlow()

    private val _uiState = MutableStateFlow<MyPageUiState>(MyPageUiState.LoggedOut())

    private val eventChannel: SendChannel<MyPageViewEvent>
        get() = _eventChannel

    private val _eventChannel = Channel<MyPageViewEvent>()

    init {
        viewModelScope.launch {
            for (event in _eventChannel) {
                onReceiveEvent(event)
            }
        }
    }

    fun emitEvent(myPageViewEvent: MyPageViewEvent) {
        viewModelScope.launch { eventChannel.send(myPageViewEvent) }
    }

    fun sendUserLoginState(userLoginState: NidOAuthLoginState) {
        viewModelScope.launch {
            when (userLoginState) {
                NidOAuthLoginState.OK -> eventChannel.send(MyPageViewEvent.OnLogin(userLoginState))
                else -> eventChannel.send(MyPageViewEvent.OnLogout(userLoginState))
            }
        }
    }

    private suspend fun onReceiveEvent(event: MyPageViewEvent) {
        when (event) {
            is MyPageViewEvent.OnTapLoginItem -> onTapLoginItem()
            is MyPageViewEvent.OnTapLogoutItem -> onTapLogoutItem()
            is MyPageViewEvent.OnTapFavoriteItem -> onTapFavoriteItem()
            is MyPageViewEvent.OnTapTermsItem -> onTapTermsItem()
            is MyPageViewEvent.OnTapSignOutItem -> onTapSignOutItem()
            is MyPageViewEvent.OnLogin -> onLogin()
            is MyPageViewEvent.OnLogout -> onLogout()
            is MyPageViewEvent.OnPressBack -> onBackPressed()
            is MyPageViewEvent.OnNavigateTo -> onNavigateTo(event.destination)
        }
    }

    private fun onLogin() {
        _uiState.value = MyPageUiState.LoggedIn()
    }

    private fun onLogout() {
        _uiState.value = MyPageUiState.LoggedOut()
    }

    private fun onNavigateTo(destination: Destination) {
        when (val currentState = _uiState.value) {
            is MyPageUiState.LoggedIn ->
                if (currentState.nextRoute == destination) {
                    _uiState.value = currentState.copy(nextRoute = null)
                }
            is MyPageUiState.LoggedOut ->
                if (currentState.nextRoute == destination) {
                    _uiState.value = currentState.copy(nextRoute = null)
                }
        }
    }

    fun onPressBack() {
        viewModelScope.launch {
            eventChannel.send(MyPageViewEvent.OnPressBack(Destination.SEARCH_SCREEN))
        }
    }

    private fun onBackPressed() {
        when (val currentState = _uiState.value) {
            is MyPageUiState.LoggedIn ->
                _uiState.value =
                    currentState.copy(nextRoute = Destination.SEARCH_SCREEN)
            is MyPageUiState.LoggedOut ->
                _uiState.value =
                    currentState.copy(nextRoute = Destination.SEARCH_SCREEN)
        }
    }

    private fun onTapLoginItem() {
        when (val currentState = _uiState.value) {
            is MyPageUiState.LoggedIn -> Unit
            is MyPageUiState.LoggedOut ->
                _uiState.value =
                    currentState.copy(nextRoute = Destination.LOGIN_SCREEN)
        }
    }

    private fun onTapLogoutItem() {
        when (_uiState.value) {
            is MyPageUiState.LoggedIn -> userRepository.logoutWithNaver()
            is MyPageUiState.LoggedOut -> Unit
        }
    }

    private fun onTapFavoriteItem() {
        when (val currentState = _uiState.value) {
            is MyPageUiState.LoggedIn ->
                _uiState.value =
                    currentState.copy(nextRoute = Destination.FAVORITE_SCREEN)
            is MyPageUiState.LoggedOut -> Unit
        }
    }

    private fun onTapTermsItem() {
        when (val currentState = _uiState.value) {
            is MyPageUiState.LoggedIn ->
                _uiState.value =
                    currentState.copy(nextRoute = Destination.TERMS_SCREEN)
            is MyPageUiState.LoggedOut ->
                _uiState.value =
                    currentState.copy(nextRoute = Destination.TERMS_SCREEN)
        }
    }

    private suspend fun onTapSignOutItem() {
        when (_uiState.value) {
            is MyPageUiState.LoggedIn -> userRepository.signOutWithNaver()
            is MyPageUiState.LoggedOut -> Unit
        }
    }

    fun onTapMenuItem(menuItem: MyPageMenu) {
        viewModelScope.launch { handleTappedMenuItem(menuItem) }
    }

    private suspend fun handleTappedMenuItem(menuItem: MyPageMenu) {
        when (menuItem) {
            MyPageMenu.Login -> eventChannel.send(MyPageViewEvent.OnTapLoginItem(menuItem))
            MyPageMenu.Logout -> eventChannel.send(MyPageViewEvent.OnTapLogoutItem(menuItem))
            MyPageMenu.Favorite -> eventChannel.send(MyPageViewEvent.OnTapFavoriteItem(menuItem))
            MyPageMenu.Terms -> eventChannel.send(MyPageViewEvent.OnTapTermsItem(menuItem))
            MyPageMenu.SignOut -> eventChannel.send(MyPageViewEvent.OnTapSignOutItem(menuItem))
            else -> {}
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MyPageViewModel(
                    userRepository = UserRepository(
                        userDataSource = UserDataSource(
                            NaverUserService.getService(),
                            NaverLoginService.getService(),
                        ),
                    ),
                )
            }
        }
    }
}
