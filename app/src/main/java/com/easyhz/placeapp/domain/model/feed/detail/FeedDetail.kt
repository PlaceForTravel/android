package com.easyhz.placeapp.domain.model.feed.detail

data class FeedDetail(
    val boardId: Int,
    val cityName: String,
    val content: String,
    val deletedDate: Any,
    val likeCount: Int,
    val modifiedDate: Any,
    val nickname: String,
    val placeResponseDTOS: List<PlaceResponseDTOS>,
    val regDate: String,
    val userId: String
)