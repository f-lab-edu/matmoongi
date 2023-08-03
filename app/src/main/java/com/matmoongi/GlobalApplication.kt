package com.matmoongi

import android.app.Application
import com.navercorp.nid.NaverIdLoginSDK

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        NaverIdLoginSDK.initialize(
            this,
            BuildConfig.NAVER_LOGIN_CLIENT_ID,
            BuildConfig.NAVER_LOGIN_CLIENT_SECRET,
            BuildConfig.NAVER_LOGIN_CLIENT_NAME,
        )
    }
}
