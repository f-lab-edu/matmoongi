package com.matmoongi.data

import android.content.Context
import android.util.Log
import com.matmoongi.BuildConfig
import com.matmoongi.network.NaverLoginService
import com.matmoongi.network.NaverUserService
import com.matmoongi.viewmodels.LoginResult
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLoginState
import com.navercorp.nid.oauth.OAuthLoginCallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

@ExperimentalCoroutinesApi
class UserDataSource(
    private val naverUserService: NaverUserService,
    private val naverLoginService: NaverLoginService,
) {
    fun fetchUserProfile(accessToken: String): UserProfile =
        naverUserService.getUserProfile(accessToken)

    suspend fun authenticateWithNaver(
        context: Context,
    ): LoginResult {
        val result = suspendCancellableCoroutine { continuation ->
            val callback = object : OAuthLoginCallback {

                override fun onSuccess() {
                    continuation.resume(LoginResult.SUCCESS, null)
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                    if (BuildConfig.DEBUG) {
                        Log.d("에러", errorCode)
                        Log.d("에러", errorDescription.toString())
                    }
                    continuation.resume(LoginResult.FAILURE, null)
                }

                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }
            }

            NaverIdLoginSDK.authenticate(context, callback)
        }

        return result
    }

    fun logoutWithNaver() {
        NaverIdLoginSDK.logout()
    }

    suspend fun signOutWithNaver(): NaverSignOutResponse? =
        retrieveAccessToken()?.let { validAccessToken ->
            naverLoginService.signOut(
                BuildConfig.NAVER_LOGIN_CLIENT_ID,
                BuildConfig.NAVER_LOGIN_CLIENT_SECRET,
                validAccessToken,
            )
        }

    fun retrieveAccessToken(): String? = NaverIdLoginSDK.getAccessToken()

    fun retrieveLoginState(): NidOAuthLoginState = NaverIdLoginSDK.getState()
}
