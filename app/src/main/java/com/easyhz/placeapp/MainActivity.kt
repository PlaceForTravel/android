package com.easyhz.placeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        NaverIdLoginSDK.initialize(this, BuildConfig.NAVER_API_CLIENT_ID, BuildConfig.NAVER_API_CLIENT_SECRET,"Login") //TODO: clientName 변경 필요
        KakaoSdk.init(this, BuildConfig.KAKAO_SDK_APP_KEY)
        setContent {
           PlaceApp()
        }
    }
}