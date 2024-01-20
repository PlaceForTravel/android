package com.easyhz.placeapp.domain.repository.user.social

import android.content.Context
import android.util.Log
import com.easyhz.placeapp.domain.model.user.User
import com.easyhz.placeapp.ui.user.LoginViewModel
import com.easyhz.placeapp.ui.user.SocialLoginType
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Kakao: SocialLogin() {
    private lateinit var onSuccessCallback: suspend (User) -> Unit

    private val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(LoginViewModel.TAG, "카카오 계정 로그인 실패", error)
        } else if (token != null) {
            handleKakaoLoginSuccess(token)
        }
    }
    override val scope: CoroutineScope by lazy { CoroutineScope(Dispatchers.IO) }


    override fun login(context: Context, onSuccess: suspend (User) -> Unit) {
        this.onSuccessCallback = onSuccess
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoTalk(context)
        } else {
            loginWithKakaoAccount(context)
        }
    }

    override fun logout() {
        UserApiClient.instance.logout {  }
    }

    private fun loginWithKakaoTalk(context: Context) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                handleKakaoLoginError(error, context)
            } else if (token != null) {
                handleKakaoLoginSuccess(token)
            }
        }
    }

    private fun loginWithKakaoAccount(context: Context) {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoLoginCallback)
    }

    // 카카오 로그인 실패 처리
    private fun handleKakaoLoginError(error: Throwable, context: Context) {
        Log.e(LoginViewModel.TAG, "카카오톡으로 로그인 실패", error)
        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우
        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
            // 의도적인 로그인 취소로 처리 (예: 뒤로 가기)
            return
        }

        // 카카오톡에 연결된 카카오 계정이 없는 경우, 카카오 계정 로그인 시도
        loginWithKakaoAccount(context)
    }

    private fun handleKakaoLoginSuccess(token: OAuthToken) {
        Log.i(LoginViewModel.TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
        UserApiClient.instance.me { user: com.kakao.sdk.user.model.User?, error ->
            if (error != null) {
                Log.e(LoginViewModel.TAG, "사용자 정보 요청 실패", error)
            } else if (user != null) {
                scope.launch {
                    profile = profile.copy(
                        userId = user.id.toString(),
                        email = user.kakaoAccount?.email,
                        phoneNum = user.kakaoAccount?.phoneNumber,
                        type = SocialLoginType.KAKAO
                    )
                    onSuccessCallback(profile)
                }
            }
        }
    }
}