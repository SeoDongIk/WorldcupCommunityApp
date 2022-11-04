package com.example.androidproject_worldcupcommunity

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "9cf20d057f837870d04ca9dccadf35c4")
    }
}