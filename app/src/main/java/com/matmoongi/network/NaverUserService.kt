package com.matmoongi.network

import com.matmoongi.response.UserProfileResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface NaverUserService {
    @GET("nid/me")
    fun fetchUserProfile(
        @Header("Authorization") accessToken: String,
    ): UserProfileResponse

    companion object {
        private const val baseUrl = "https://openapi.naver.com/v1/"

        private val naverUserService =
            ServiceFactory.createRetrofitService<NaverUserService>(baseUrl)

        fun getService(): NaverUserService = naverUserService
    }
}
