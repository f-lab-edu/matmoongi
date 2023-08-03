package com.matmoongi.network

import retrofit2.converter.gson.GsonConverterFactory

object GsonConverter {
    val gSonConverter: GsonConverterFactory = GsonConverterFactory.create()
}
