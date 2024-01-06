package com.easyhz.placeapp.domain.model.feed

data class Content(
    val boardId: Int,
    val cityName: String,
    val imgUrl: List<String>,
    var likeCount: Int,
    val nickname: String,
    val regDate: String,
    val modifiedDate: String?,
    val deletedDate: String?,
    val userId: String,
    val text: String?,
    val places: String?,
)