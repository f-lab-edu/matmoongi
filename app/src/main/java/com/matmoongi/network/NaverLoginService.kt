package com.matmoongi.network

import com.matmoongi.data.UserProfile
import retrofit2.http.GET
import retrofit2.http.Header

interface NaverLoginService {
    @GET("nid/me")
    fun getUserProfile(
        @Header("Authorization") accessToken: String,
    ): UserProfile

    companion object {
        private const val baseUrl = "https://openapi.naver.com/v1/"

        private val naverLoginService =
            ServiceFactory.createRetrofitService<NaverLoginService>(baseUrl)

        fun getService(): NaverLoginService = naverLoginService
    }
}
