package com.easyhz.placeapp.domain.repository.user.social

import android.content.Context
import android.util.Log
import com.easyhz.placeapp.domain.model.user.User
import com.easyhz.placeapp.ui.user.LoginViewModel
import com.easyhz.placeapp.ui.user.SocialLoginType
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfile
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

object Naver: SocialLogin() {
    private lateinit var onSuccessCallback: suspend (User) -> Unit

    private val naverLoginCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            scope.launch {
                getNidProfile()?.let {
                    profile = profile.copy(
                        userId = it.id!!,
                        email = it.email,
                        phoneNum = it.mobile,
                        type = SocialLoginType.NAVER
                    )
                    onSuccessCallback(profile)
                }
            }
        }
        override fun onError(errorCode: Int, message: String) {
            Log.d(LoginViewModel.TAG, "oAuthLoginCallback > onError - errorCode: $errorCode , message: $message")
        }
        override fun onFailure(httpStatus: Int, message: String) {
            Log.d(LoginViewModel.TAG, "oAuthLoginCallback > onFailure - httpStatus: $httpStatus , message: $message")
        }
    }
    override val scope: CoroutineScope by lazy { CoroutineScope(Dispatchers.IO) }

    override fun login(context: Context, onSuccess: suspend (User) -> Unit) {
        this.onSuccessCallback = onSuccess
        NaverIdLoginSDK.authenticate(context, naverLoginCallback)
    }

    override fun logout() {
        NaverIdLoginSDK.logout()
    }

    private suspend fun getNidProfile(): NidProfile?  = suspendCancellableCoroutine { continuation ->
        NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(result: NidProfileResponse) {
                result.profile?.let { profile ->
                    continuation.resume(profile)
                } ?: run { continuation.resume(null) }
            }
            override fun onError(errorCode: Int, message: String) {
                Log.d(LoginViewModel.TAG, "getNidProfile > onError - errorCode: $errorCode , message: $message")
                continuation.resume(null)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.d(LoginViewModel.TAG, "getNidProfile > onFailure - httpStatus: $httpStatus , message: $message")
                continuation.resume(null)
            }
        })
    }

}