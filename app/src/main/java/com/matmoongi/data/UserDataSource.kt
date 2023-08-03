package com.matmoongi.data

import android.content.Context
import com.matmoongi.network.NaverLoginService
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLoginState
import com.navercorp.nid.oauth.OAuthLoginCallback
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserDataSource(
    private val naverLoginService: NaverLoginService,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun fetchUserProfile(accessToken: String): UserProfile =
        withContext(ioDispatcher) {
            naverLoginService.getUserProfile(accessToken)
        }

    fun authenticateWithNaver(context: Context, oAuthLoginCallback: OAuthLoginCallback) =
        NaverIdLoginSDK.authenticate(
            context,
            oAuthLoginCallback,
        )

    fun retrieveAccessToken(): String = NaverIdLoginSDK.getAccessToken().orEmpty()

    fun retrieveLoginState(): NidOAuthLoginState = NaverIdLoginSDK.getState()
}
