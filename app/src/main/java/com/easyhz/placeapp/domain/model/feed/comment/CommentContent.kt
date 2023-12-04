package com.easyhz.placeapp.domain.model.feed.comment

data class CommentContent(
    val commentId: Int,
    val content: String,
    val nickname: String,
    val regDate: String,
    val userId: String
)