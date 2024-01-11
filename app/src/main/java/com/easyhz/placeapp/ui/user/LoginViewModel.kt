package com.easyhz.placeapp.ui.user

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.easyhz.placeapp.domain.model.user.LoginSteps
import com.easyhz.placeapp.domain.model.user.UserState
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(

): ViewModel() {
    val TAG = this::class.java.simpleName
    var userState by mutableStateOf(UserState())
    val oAuthLoginCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            getNidProfile()
        }
        override fun onError(errorCode: Int, message: String) {
            Log.d(TAG, "oAuthLoginCallback > onError - errorCode: $errorCode , message: $message")
        }
        override fun onFailure(httpStatus: Int, message: String) {
            Log.d(TAG, "oAuthLoginCallback > onFailure - httpStatus: $httpStatus , message: $message")
        }
    }

    fun getNidProfile() {
        NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(result: NidProfileResponse) {
                if (result.profile != null) {
                    // TODO:: 미가입 유저일 경우.. (api 연결 필요)
                    userState = userState.copy(step = LoginSteps.USERNAME)
                }
                Log.d("LoginViewModel", "네이버 로그인한 유저 정보 - ${result.profile}")
                // 네이버 로그인 인증 성공
                println("로그인 성공")
                println("AccessToken -> ${NaverIdLoginSDK.getAccessToken()}")
                println("RefreshToken -> ${NaverIdLoginSDK.getRefreshToken()}")
                println("Expires -> ${NaverIdLoginSDK.getExpiresAt()}")
                println("Type -> ${NaverIdLoginSDK.getTokenType()}")
                println("State -> ${NaverIdLoginSDK.getState()}")
            }

            override fun onError(errorCode: Int, message: String) {
                Log.d(TAG, "getNidProfile > onError - errorCode: $errorCode , message: $message")
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.d(TAG, "getNidProfile > onFailure - httpStatus: $httpStatus , message: $message")
            }
        })
    }

    fun manageNavigateToBack(onNavigateToBack: () -> Unit) {
        when(userState.step) {
            LoginSteps.LOGIN -> onNavigateToBack()
            LoginSteps.USERNAME -> setUserStateStep(LoginSteps.LOGIN)
            LoginSteps.SUCCESS -> { }
        }
    }

    fun setUserStateStep(value: LoginSteps) {
        userState = userState.copy(step = value)
    }
}