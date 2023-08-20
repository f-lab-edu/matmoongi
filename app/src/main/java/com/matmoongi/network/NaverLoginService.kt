package com.matmoongi.network

import com.matmoongi.response.NaverSignOutResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverLoginService {
    @GET("token")
    suspend fun signOut(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("access_token") accessToken: String,
        @Query("grant_type") grantType: String = "delete",
        @Query("service_provider") serviceProvider: String = "Naver",
    ): NaverSignOutResponse

    companion object {
        private const val baseUrl = "https://nid.naver.com/oauth2.0/"

        private val naverLoginService =
            ServiceFactory.createRetrofitService<NaverLoginService>(baseUrl)

        fun getService(): NaverLoginService = naverLoginService
    }
}
