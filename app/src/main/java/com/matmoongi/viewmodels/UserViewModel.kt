@file:OptIn(ExperimentalCoroutinesApi::class)

package com.matmoongi.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.matmoongi.Destination
import com.matmoongi.LoginEvent
import com.matmoongi.UserUiState
import com.matmoongi.UserViewEvent
import com.matmoongi.data.UserDataSource
import com.matmoongi.data.UserRepository
import com.matmoongi.network.NaverLoginService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus

class UserViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    val uiState: StateFlow<UserUiState>
        get() = _uiState.asStateFlow()

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.LoggedOut())

    private val eventChannel: SendChannel<UserViewEvent>
        get() = _eventChannel

    private val _eventChannel = Channel<UserViewEvent>()

    override fun onCleared() {
        super.onCleared()
        _eventChannel.close()
    }

    private fun refreshUserLoginState() {
        when (val result = userRepository.retrieveUserLoginState()) {
            is UserUiState.LoggedIn -> {
                _uiState.value = result.copy(nextRoute = Destination.SEARCH_SCREEN)
                EventBus.getDefault().post(LoginEvent.Login(result.loginStatus))
            }
            is UserUiState.LoggedOut -> {
                _uiState.value = result
                EventBus.getDefault().post(LoginEvent.Logout(result.loginStatus))
            }
        }
    }

    init {
        viewModelScope.launch {
            for (event in _eventChannel) {
                onReceiveEvent(event)
            }
        }
    }

    fun emitEvent(userViewEvent: UserViewEvent) {
        viewModelScope.launch { eventChannel.send(userViewEvent) }
    }

    private fun onReceiveEvent(event: UserViewEvent) {
        when (event) {
            is UserViewEvent.OnTapLoginButton -> tryToLogin(event.context)
            is UserViewEvent.OnTapSkipLoginButton -> onSkipLogin()
            is UserViewEvent.OnAutoLogin -> onAutoLogin(event.context)
            is UserViewEvent.OnNavigateTo -> onNavigateTo(event.destination)
        }
    }

    private fun tryToLogin(
        context: Context,
    ) {
        viewModelScope.launch {
            val loginResult = withContext(Dispatchers.IO) {
                userRepository.loginWithNaver(context)
            }
            loginResult.fold(
                onSuccess = { refreshUserLoginState() },
                onFailure = { TODO("실패 스낵바 or 토스트메세지") },
            )
        }
    }

    private fun onSkipLogin() {
        _uiState.value = UserUiState.LoggedOut(nextRoute = Destination.SEARCH_SCREEN)
    }

    private fun onAutoLogin(
        context: Context,
    ) {
        userRepository.retrieveAccessToken()?.let {
            tryToLogin(context)
        }
    }

    private fun onNavigateTo(destination: Destination) {
        when (val currentState = _uiState.value) {
            is UserUiState.LoggedIn ->
                if (currentState.nextRoute == destination) {
                    _uiState.value = currentState.copy(nextRoute = null)
                }
            is UserUiState.LoggedOut ->
                if (currentState.nextRoute == destination) {
                    _uiState.value = currentState.copy(nextRoute = null)
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                UserViewModel(
                    userRepository = UserRepository(
                        userDataSource = UserDataSource(
                            NaverLoginService.getService(),
                        ),
                    ),
                )
            }
        }
    }
}
