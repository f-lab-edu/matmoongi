@file:OptIn(ExperimentalCoroutinesApi::class)

package com.matmoongi.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.matmoongi.data.UserDataSource
import com.matmoongi.data.UserRepository
import com.matmoongi.network.NaverLoginService
import com.matmoongi.network.NaverUserService
import com.navercorp.nid.oauth.NidOAuthLoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class LoginResult {
    SUCCESS, FAILURE
}

class UserViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    val userLoginState: MutableLiveData<NidOAuthLoginState> =
        MutableLiveData<NidOAuthLoginState>(userRepository.retrieveUserLoginState())

    fun autoLogin(
        context: Context,
        goToSearchScreen: () -> Unit,
    ) {
        userRepository.retrieveAccessToken()?.let {
            tryToLogin(context, goToSearchScreen)
        }
    }

    private fun refreshUserLoginState() {
        userLoginState.value = userRepository.retrieveUserLoginState()
    }

    fun onClickLoginButton(
        context: Context,
        goToSearchScreen: () -> Unit,
    ) {
        tryToLogin(context, goToSearchScreen)
    }

    fun onClickLogoutButton(goToLoginScreen: () -> Unit): () -> Unit = {
        logoutStep(goToLoginScreen)
    }

    fun onClickSignOutButton(goToLoginScreen: () -> Unit): () -> Unit = {
        viewModelScope.launch {
            val response =
                withContext(Dispatchers.IO) { userRepository.signOutWithNaver() }
            response?.let {
                if (it.result == "success") {
                    logoutStep(goToLoginScreen)
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                UserViewModel(
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

    /**
     * 로그아웃 -> 로그인 상태 업데이트 -> 로그인 화면으로 이동
     */
    private fun logoutStep(goToLoginScreen: () -> Unit) {
        userRepository.logoutWithNaver()
            .also { refreshUserLoginState() }
            .also { goToLoginScreen() }
    }

    private fun tryToLogin(
        context: Context,
        goToSearchScreen: () -> Unit,
    ) {
        viewModelScope.launch {
            val loginResult =
                withContext(Dispatchers.IO) {
                    userRepository.loginWithNaver(context)
                }
            if (loginResult == LoginResult.SUCCESS) {
                refreshUserLoginState().also { goToSearchScreen() }
            }
        }
    }
}
