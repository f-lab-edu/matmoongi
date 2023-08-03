package com.matmoongi.data

import android.content.Context
import com.navercorp.nid.oauth.OAuthLoginCallback

class UserRepository(private val userDataSource: UserDataSource) {
    fun loginWithNaver(context: Context, oAuthLoginCallback: OAuthLoginCallback) =
        userDataSource.authenticateWithNaver(context, oAuthLoginCallback)

    fun retrieveUserLoginState() = userDataSource.retrieveLoginState()
}
