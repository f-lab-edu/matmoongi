package com.matmoongi.data

import android.content.Context
import com.matmoongi.viewmodels.LoginResult
import com.navercorp.nid.oauth.NidOAuthLoginState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class UserRepository(private val userDataSource: UserDataSource) {
    suspend fun loginWithNaver(context: Context): LoginResult =
        userDataSource.authenticateWithNaver(context)

    fun logoutWithNaver(): Unit = userDataSource.logoutWithNaver()

    suspend fun signOutWithNaver(): NaverSignOutResponse? = userDataSource.signOutWithNaver()

    fun retrieveUserLoginState(): NidOAuthLoginState = userDataSource.retrieveLoginState()

    fun retrieveAccessToken(): String? = userDataSource.retrieveAccessToken()
}
