package com.easyhz.placeapp.domain.model.user

import com.easyhz.placeapp.ui.user.SocialLoginType

data class UserState(
    val user: User = User(),
    val step: LoginSteps = LoginSteps.LOGIN,
    val isValid: Boolean = false,
    val onSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class LoginSteps() {
    LOGIN, USERNAME, SUCCESS
}

fun UserState.update(
    userId: String = user.userId,
    nickname: String = user.nickname,
    email: String? = user.email,
    fcmToken: String? = user.fcmToken,
    phoneNum: String? = user.phoneNum,
    type: SocialLoginType = user.type
): UserState {
    val user = user.copy(
        userId = userId,
        nickname = nickname,
        email = email,
        fcmToken = fcmToken,
        phoneNum = phoneNum,
        type = type
    )

    return copy(user = user)
}
