package com.easyhz.placeapp.api.network

import okhttp3.Interceptor
import okhttp3.Response

class ServiceInterceptor(
    clientId: String? = null,
    clientSecret: String? = null,
    userToken: String? = null,
):Interceptor {

    // 외부 접근 못 하도록
    companion object {
        var xClientId: String? = null
        var xClientSecret: String? = null
    }

    init {
        xClientId = clientId
        xClientSecret = clientSecret
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!xClientId.isNullOrEmpty() && !xClientSecret.isNullOrEmpty()) {
            request = request.newBuilder().apply {
                xClientId?.let { addHeader("X-Naver-Client-Id", it) }
                xClientSecret?.let { addHeader("X-Naver-Client-Secret", it) }
            }.build()
        }

        return chain.proceed(request)



    }
}