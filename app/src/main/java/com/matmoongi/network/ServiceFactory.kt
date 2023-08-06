package com.matmoongi.network

import com.matmoongi.network.GsonConverter.gsonConverter
import com.matmoongi.network.OkHttpClient.client
import retrofit2.Retrofit

object ServiceFactory {
    inline fun <reified T : Any> createRetrofitService(baseUrl: String): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(gsonConverter)
            .build()
            .create(T::class.java)
}
