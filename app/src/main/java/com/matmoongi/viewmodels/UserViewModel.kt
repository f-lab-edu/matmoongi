package com.matmoongi.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.matmoongi.BuildConfig
import com.matmoongi.data.UserDataSource
import com.matmoongi.data.UserRepository
import com.matmoongi.network.NaverLoginService
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLoginState
import com.navercorp.nid.oauth.OAuthLoginCallback
import kotlinx.coroutines.Dispatchers

class UserViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    val userLoginState: MutableLiveData<NidOAuthLoginState> =
        MutableLiveData<NidOAuthLoginState>(userRepository.retrieveUserLoginState())

    inline fun oAuthLoginCallback(crossinline goToSearchScreen: () -> Unit): OAuthLoginCallback =
        object : OAuthLoginCallback {
            override fun onSuccess() {
                refreshUserLoginState()
                goToSearchScreen()
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                if (BuildConfig.DEBUG) {
                    Log.d("에러", errorCode)
                    Log.d("에러", errorDescription.toString())
                }
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

    fun refreshUserLoginState() {
        userLoginState.value = userRepository.retrieveUserLoginState()
    }

    fun onClickNaverLoginButton(
        context: Context,
        oAuthLoginCallback: OAuthLoginCallback,
    ): () -> Unit =
        { userRepository.loginWithNaver(context, oAuthLoginCallback) }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                UserViewModel(
                    userRepository = UserRepository(
                        userDataSource = UserDataSource(
                            NaverLoginService.getService(),
                            Dispatchers.IO,
                        ),
                    ),
                )
            }
        }
    }
}
