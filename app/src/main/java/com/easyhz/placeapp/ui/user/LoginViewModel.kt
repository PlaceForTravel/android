package com.easyhz.placeapp.ui.user

import androidx.lifecycle.ViewModel
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(

): ViewModel() {
    val oauthLoginCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            // 네이버 로그인 인증 성공
            println("로그인 성공")
            println("AccessToken -> ${NaverIdLoginSDK.getAccessToken()}")
            println("RefreshToken -> ${NaverIdLoginSDK.getRefreshToken()}")
            println("Expires -> ${NaverIdLoginSDK.getExpiresAt()}")
            println("Type -> ${NaverIdLoginSDK.getTokenType()}")
            println("State -> ${NaverIdLoginSDK.getState()}")
        }
        override fun onFailure(httpStatus: Int, message: String) {
            val errorCode = NaverIdLoginSDK.getLastErrorCode().code
            val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
            println("errorCode:$errorCode, errorDesc:$errorDescription")
        }
        override fun onError(errorCode: Int, message: String) {
            onFailure(errorCode, message)
        }
    }
}