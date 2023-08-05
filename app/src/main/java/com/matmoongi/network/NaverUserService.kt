package com.matmoongi.network

import com.matmoongi.data.UserProfile
import retrofit2.http.GET
import retrofit2.http.Header

interface NaverUserService {
    @GET("nid/me")
    fun getUserProfile(
        @Header("Authorization") accessToken: String,
    ): UserProfile

    companion object {
        private const val baseUrl = "https://openapi.naver.com/v1/"

        private val naverUserService =
            ServiceFactory.createRetrofitService<NaverUserService>(baseUrl)

        fun getService(): NaverUserService = naverUserService
    }
}
