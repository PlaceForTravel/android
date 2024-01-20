package com.easyhz.placeapp.domain.repository.user.social

import android.content.Context
import com.easyhz.placeapp.domain.model.user.User
import kotlinx.coroutines.CoroutineScope

abstract class SocialLogin {
    var profile: User = User()
        protected set

    abstract val scope: CoroutineScope

    abstract fun login(context: Context, onSuccess: suspend (User) -> Unit)

    abstract fun logout()

    fun setNickname(nickname: String) {
        profile = profile.copy(nickname = nickname)
    }
}