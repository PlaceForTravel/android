package com.easyhz.placeapp.domain.model.user

import com.easyhz.placeapp.ui.user.SocialLoginType


data class User(
    val userId: String = "",
    val nickname: String = "",
    val email: String? = null,
    val fcmToken: String? = null,
    val phoneNum: String? = null,
    val type: SocialLoginType = SocialLoginType.NONE
)

object UserManager {
    var user: User? = null
        private set
    val needLogin: Boolean
        get() = user == null || user?.userId?.isEmpty() == true
    fun setUser(newUser: User) {
        user = newUser
    }

    fun clearUser() {
        user = null
    }

}