package com.matmoongi.data

import android.content.Context
import android.util.Log
import com.matmoongi.BuildConfig
import com.matmoongi.SuccessOrFailure
import com.matmoongi.network.NaverLoginService
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLoginState
import com.navercorp.nid.oauth.OAuthLoginCallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

@ExperimentalCoroutinesApi
class UserDataSource(
    private val naverLoginService: NaverLoginService,
) {
    suspend fun authenticateWithNaver(
        context: Context,
    ): Result<SuccessOrFailure> {
        val result = suspendCancellableCoroutine { continuation ->
            val callback = object : OAuthLoginCallback {

                override fun onSuccess() {
                    continuation.resume(Result.success(SuccessOrFailure.Success), null)
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                    if (BuildConfig.DEBUG) {
                        Log.d("에러", errorCode)
                        Log.d("에러", errorDescription.toString())
                    }
                    continuation.resume(
                        Result.failure(
                            Exception(errorDescription ?: "unknown error"),
                        ),
                        null,
                    )
                }

                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }
            }

            NaverIdLoginSDK.authenticate(context, callback)
        }

        return result
    }

    fun logoutWithNaver(): Result<SuccessOrFailure> =
        try {
            NaverIdLoginSDK.logout()
            Result.success(SuccessOrFailure.Success)
        } catch (e: Exception) {
            Result.failure(e)
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
