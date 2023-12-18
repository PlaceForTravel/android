package com.easyhz.placeapp.domain.model.feed

data class SavePostState(
    val userInfo: UserInfo = UserInfo(),
    val isSuccessful: Boolean = false,
    val error: String? = null
)

data class UserInfo(
    val userId: String = "user1"
)