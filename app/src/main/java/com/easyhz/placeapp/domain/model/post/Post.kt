package com.easyhz.placeapp.domain.model.post

data class Post(
    val content: String?,
    val cityName: String,
    val userId: String,
    val nickname: String,
    val places: List<Place>
)
