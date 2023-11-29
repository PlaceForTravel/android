package com.easyhz.placeapp.domain.model.feed

data class Content(
    val boardId: Int,
    val cityName: String,
    val deletedDate: Any,
    val imgUrl: List<String>,
    val likeCount: Int,
    val modifiedDate: Any,
    val nickname: String,
    val regDate: String,
    val userId: String,
    val text: String?,
    val places: String?,
)