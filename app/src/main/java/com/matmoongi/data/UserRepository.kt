package com.matmoongi.data

import android.content.Context
import com.matmoongi.SuccessOrFailure
import com.matmoongi.UserUiState
import com.navercorp.nid.oauth.NidOAuthLoginState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class UserRepository(private val userDataSource: UserDataSource) {
    suspend fun loginWithNaver(context: Context): Result<SuccessOrFailure> =
        userDataSource.authenticateWithNaver(context)

    fun logoutWithNaver(): Result<SuccessOrFailure> = userDataSource.logoutWithNaver()

    suspend fun signOutWithNaver(): Result<SuccessOrFailure> {
        val result = userDataSource.signOutWithNaver()
        return if (result?.result == "success") {
            Result.success(SuccessOrFailure.Success)
        } else {
            Result.failure(Exception(result?.result))
        }
    }

    fun retrieveUserLoginState(): UserUiState {
        return when (userDataSource.retrieveLoginState()) {
            NidOAuthLoginState.OK -> UserUiState.LoggedIn()
            else -> UserUiState.LoggedOut()
        }
    }

    fun retrieveAccessToken(): String? = userDataSource.retrieveAccessToken()
}
