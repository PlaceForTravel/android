package com.easyhz.placeapp.domain.model.user


data class User(
    val userId: String = "user1",
    val nickname: String = "user1",
    val email: String = "",
    val fcmToken: String = "",
    val phoneNum: String = "",
    val type: LoginTypes = LoginTypes.NONE
)

enum class LoginTypes() {
    NONE, NAVER, KAKAO
}

object UserManager {
    var user: User? = null
        private set

    fun setUser(newUser: User) {
        user = newUser
    }

    fun clearUser() {
        user = null
    }

}