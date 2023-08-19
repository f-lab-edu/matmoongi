package com.matmoongi.data.repository

import android.content.Context
import com.matmoongi.SuccessOrFailure
import com.matmoongi.data.datasource.LoginDataSource
import com.matmoongi.login.LoginUiState
import com.navercorp.nid.oauth.NidOAuthLoginState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class LoginRepository(private val loginDataSource: LoginDataSource) {
    suspend fun loginWithNaver(context: Context): Result<SuccessOrFailure> =
        loginDataSource.authenticateWithNaver(context)

    fun logoutWithNaver(): Result<SuccessOrFailure> = loginDataSource.logoutWithNaver()

    suspend fun signOutWithNaver(): Result<SuccessOrFailure> {
        val result = loginDataSource.signOutWithNaver()
        return if (result?.result == "success") {
            Result.success(SuccessOrFailure.Success)
        } else {
            Result.failure(Exception(result?.result))
        }
    }

    fun retrieveLoginState(): LoginUiState {
        return when (loginDataSource.retrieveLoginState()) {
            NidOAuthLoginState.OK -> LoginUiState.LoggedIn()
            else -> LoginUiState.LoggedOut()
        }
    }

    fun retrieveAccessToken(): String? = loginDataSource.retrieveAccessToken()
}
