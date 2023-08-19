@file:OptIn(ExperimentalCoroutinesApi::class)

package com.matmoongi.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.matmoongi.Destination
import com.matmoongi.data.datasource.LoginDataSource
import com.matmoongi.data.repository.LoginRepository
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

class LoginViewModel(
    private val loginRepository: LoginRepository,
) : ViewModel() {

    val uiState: StateFlow<LoginUiState>
        get() = _uiState.asStateFlow()

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.LoggedOut())

    private val eventChannel: SendChannel<LoginViewEvent>
        get() = _eventChannel

    private val _eventChannel = Channel<LoginViewEvent>()

    override fun onCleared() {
        super.onCleared()
        _eventChannel.close()
    }

    private fun refreshLoginState() {
        when (val result = loginRepository.retrieveLoginState()) {
            is LoginUiState.LoggedIn -> {
                _uiState.value = result.copy(nextRoute = Destination.SEARCH_SCREEN)
                EventBus.getDefault().post(LoginEvent.Login(result.loginStatus))
            }
            is LoginUiState.LoggedOut -> {
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

    fun emitEvent(loginViewEvent: LoginViewEvent) {
        viewModelScope.launch { eventChannel.send(loginViewEvent) }
    }

    private fun onReceiveEvent(event: LoginViewEvent) {
        when (event) {
            is LoginViewEvent.OnTapLoginButton -> tryToLogin(event.context)
            is LoginViewEvent.OnTapSkipLoginButton -> onSkipLogin()
            is LoginViewEvent.OnAutoLogin -> onAutoLogin(event.context)
            is LoginViewEvent.OnNavigateTo -> onNavigateTo(event.destination)
        }
    }

    private fun tryToLogin(
        context: Context,
    ) {
        viewModelScope.launch {
            val loginResult = withContext(Dispatchers.IO) {
                loginRepository.loginWithNaver(context)
            }
            loginResult.fold(
                onSuccess = { refreshLoginState() },
                onFailure = { TODO("실패 스낵바 or 토스트메세지") },
            )
        }
    }

    private fun onSkipLogin() {
        _uiState.value = LoginUiState.LoggedOut(nextRoute = Destination.SEARCH_SCREEN)
    }

    private fun onAutoLogin(
        context: Context,
    ) {
        loginRepository.retrieveAccessToken()?.let {
            tryToLogin(context)
        }
    }

    private fun onNavigateTo(destination: Destination) {
        when (val currentState = _uiState.value) {
            is LoginUiState.LoggedIn ->
                if (currentState.nextRoute == destination) {
                    _uiState.value = currentState.copy(nextRoute = null)
                }
            is LoginUiState.LoggedOut ->
                if (currentState.nextRoute == destination) {
                    _uiState.value = currentState.copy(nextRoute = null)
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LoginViewModel(
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
