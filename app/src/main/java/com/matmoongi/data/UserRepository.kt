package com.matmoongi.data

import android.content.Context
import com.matmoongi.viewmodels.LoginResult
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class UserRepository(private val userDataSource: UserDataSource) {
    suspend fun loginWithNaver(context: Context): LoginResult =
        userDataSource.authenticateWithNaver(context)

    fun logoutWithNaver() = userDataSource.logoutWithNaver()

    suspend fun signOutWithNaver() = userDataSource.signOutWithNaver()

    fun retrieveUserLoginState() = userDataSource.retrieveLoginState()

    fun retrieveAccessToken() = userDataSource.retrieveAccessToken()
}
