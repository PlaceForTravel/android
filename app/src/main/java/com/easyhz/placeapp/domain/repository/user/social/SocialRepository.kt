package com.easyhz.placeapp.domain.repository.user.social

import android.content.Context
import com.easyhz.placeapp.domain.model.user.User
import kotlinx.coroutines.CoroutineScope

interface SocialRepository {
    val scope: CoroutineScope

    fun login(context: Context, onSuccess: suspend (User) -> Unit)

    fun logout()

    fun setNickname(nickname: String)
}