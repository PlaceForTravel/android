package com.easyhz.placeapp.domain.model.user

data class UserState(
    val user: User = User(),
    val step: LoginSteps = LoginSteps.LOGIN,
    val onSuccess: Boolean = false,
    val error: String? = null
)

enum class LoginSteps() {
    LOGIN, USERNAME, SUCCESS
}
