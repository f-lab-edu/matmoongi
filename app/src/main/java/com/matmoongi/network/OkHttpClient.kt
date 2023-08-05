package com.matmoongi.network

import com.matmoongi.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpClient {
    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    val client = OkHttpClient
        .Builder()
        .addInterceptorIfDebug(logger)
        .build()
}

private fun OkHttpClient.Builder.addInterceptorIfDebug(logger: HttpLoggingInterceptor):
    OkHttpClient.Builder =
    this.apply {
        if (BuildConfig.DEBUG) addInterceptor(logger)
    }
