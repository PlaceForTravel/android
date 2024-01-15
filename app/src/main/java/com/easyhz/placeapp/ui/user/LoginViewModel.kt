package com.easyhz.placeapp.ui.user

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.easyhz.placeapp.domain.model.user.LoginSteps
import com.easyhz.placeapp.domain.model.user.UserState
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
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
    /* 네이버 로그인 콜백 */
    val naverLoginCallback = object : OAuthLoginCallback {
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
    /* 카카오 로그인 콜백 */
    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
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
    fun tryLoginWithKakao(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoTalk(context)
        } else {
            loginWithKakaoAccount(context)
        }
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

    // 카카오톡으로 로그인 시도
    private fun loginWithKakaoTalk(context: Context) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                handleKakaoLoginError(error, context)
            } else if (token != null) {
                handleKakaoLoginSuccess(token)
            }
        }
    }

    // 카카오 계정 로그인 시도
    private fun loginWithKakaoAccount(context: Context) {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoLoginCallback)
    }

    // 카카오 로그인 실패 처리
    private fun handleKakaoLoginError(error: Throwable, context: Context) {
        Log.e(TAG, "카카오톡으로 로그인 실패", error)
        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우
        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
            // 의도적인 로그인 취소로 처리 (예: 뒤로 가기)
            return
        }

        // 카카오톡에 연결된 카카오 계정이 없는 경우, 카카오 계정 로그인 시도
        loginWithKakaoAccount(context)
    }

    // 카카오톡 로그인 성공 처리
    private fun handleKakaoLoginSuccess(token: OAuthToken) {
        Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
        // 추가적인 로직이 있다면 여기에서 처리
    }
}