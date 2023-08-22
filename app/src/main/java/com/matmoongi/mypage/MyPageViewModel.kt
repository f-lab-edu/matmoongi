@file:OptIn(ExperimentalCoroutinesApi::class)

package com.matmoongi.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.matmoongi.Destination
import com.matmoongi.data.datasource.LoginDataSource
import com.matmoongi.data.repository.LoginRepository
import com.matmoongi.login.LoginEvent
import com.matmoongi.network.NaverLoginService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

enum class MyPageMenu {
    Login, Logout, Favorite, Version, Terms, SignOut
}

class MyPageViewModel(
    private val loginRepository: LoginRepository,
) : ViewModel() {

    val uiState: StateFlow<MyPageUiState>
        get() = _uiState.asStateFlow()

    private val _uiState = MutableStateFlow<MyPageUiState>(MyPageUiState.LoggedOut())

    private val eventChannel: SendChannel<MyPageViewEvent>
        get() = _eventChannel

    private val _eventChannel = Channel<MyPageViewEvent>()

    init {
        EventBus.getDefault().register(this)
        viewModelScope.launch {
            for (event in _eventChannel) {
                onReceiveEvent(event)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(event: LoginEvent) {
        viewModelScope.launch {
            when (event) {
                is LoginEvent.Login -> eventChannel.send(MyPageViewEvent.OnLogin(event.loginStatus))
                is LoginEvent.Logout -> eventChannel.send(
                    MyPageViewEvent.OnLogout(event.loginStatus),
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _eventChannel.close()
        EventBus.getDefault().unregister(this)
    }

    fun emitEvent(myPageViewEvent: MyPageViewEvent) {
        viewModelScope.launch { eventChannel.send(myPageViewEvent) }
    }

    private suspend fun onReceiveEvent(event: MyPageViewEvent) {
        when (event) {
            is MyPageViewEvent.OnLogin -> onLogin()
            is MyPageViewEvent.OnLogout -> onLogout()
            is MyPageViewEvent.OnTapMenuItem -> onTapMenuItem(event.menuItem)
            is MyPageViewEvent.OnTapLoginItem -> onTapLoginItem()
            is MyPageViewEvent.OnTapLogoutItem -> onTapLogoutItem()
            is MyPageViewEvent.OnTapFavoriteItem -> onTapFavoriteItem()
            is MyPageViewEvent.OnTapTermsItem -> onTapTermsItem()
            is MyPageViewEvent.OnTapSignOutItem -> onTapSignOutItem()
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
            is MyPageUiState.LoggedIn -> {
                loginRepository.logoutWithNaver().fold(
                    onSuccess = {
                        _uiState.value = MyPageUiState.LoggedOut(
                            nextRoute = Destination.LOGIN_SCREEN,
                        )
                    },
                    onFailure = {},
                )
            }
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
            is MyPageUiState.LoggedIn -> loginRepository.signOutWithNaver().fold(
                onSuccess = {
                    _uiState.value = MyPageUiState.LoggedOut(nextRoute = Destination.LOGIN_SCREEN)
                },
                onFailure = { TODO("실패 스낵바 or 토스트 메세지") },
            )
            is MyPageUiState.LoggedOut -> Unit
        }
    }

    private fun onTapMenuItem(menuItem: MyPageMenu) {
        viewModelScope.launch {
            when (menuItem) {
                MyPageMenu.Login -> eventChannel.send(MyPageViewEvent.OnTapLoginItem(menuItem))
                MyPageMenu.Logout -> eventChannel.send(MyPageViewEvent.OnTapLogoutItem(menuItem))
                MyPageMenu.Favorite -> eventChannel.send(
                    MyPageViewEvent.OnTapFavoriteItem(menuItem),
                )
                MyPageMenu.Terms -> eventChannel.send(MyPageViewEvent.OnTapTermsItem(menuItem))
                MyPageMenu.SignOut -> eventChannel.send(MyPageViewEvent.OnTapSignOutItem(menuItem))
                MyPageMenu.Version -> Unit
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MyPageViewModel(
                    loginRepository = LoginRepository(
                        loginDataSource = LoginDataSource(
                            NaverLoginService.getService(),
                        ),
                    ),
                )
            }
        }
    }
}
