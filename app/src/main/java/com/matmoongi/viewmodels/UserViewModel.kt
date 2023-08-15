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

    private fun refreshUserLoginState() {
        userLoginState.value = userRepository.retrieveUserLoginState()
    }

    fun onClickNaverLoginButton(
        context: Context,
        goToSearchScreen: () -> Unit,
    ) {
        viewModelScope.launch {
            val loginResult =
                withContext(Dispatchers.IO) {
                    userRepository.loginWithNaver(context)
                }
            if (loginResult == LoginResult.SUCCESS) {
                refreshUserLoginState()
                goToSearchScreen()
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
