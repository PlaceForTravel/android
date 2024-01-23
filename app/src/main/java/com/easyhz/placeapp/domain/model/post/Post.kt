package com.easyhz.placeapp.domain.model.post

import com.easyhz.placeapp.domain.model.user.UserManager

data class Post(
    val content: String = "",
    val cityName: String = "",
    val userId: String? = UserManager.user?.userId,
    val nickname: String? = UserManager.user?.nickname,
    val places: List<Place> = listOf(),
)


data class ModifyPost(
    val content: String = "",
    val userId: String? = UserManager.user?.userId,
    val nickname: String? = UserManager.user?.nickname,
)