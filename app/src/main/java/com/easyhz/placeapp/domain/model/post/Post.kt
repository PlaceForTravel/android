package com.easyhz.placeapp.domain.model.post

data class Post(
    val content: String = "",
    val cityName: String = "",
    val userId: String = "user1",
    val nickname: String = "user1",
    val places: List<Place> = listOf(),
)
