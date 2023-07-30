package com.matmoongi

import android.app.Application
import com.navercorp.nid.NaverIdLoginSDK

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        NaverIdLoginSDK.initialize(
            this,
            BuildConfig.NAVER_LOGIN_CLINET_ID,
            BuildConfig.NAVER_LOGIN_CLINET_SECRET,
            BuildConfig.NAVER_LOGIN_CLINET_NAME,
        )
    }
}
