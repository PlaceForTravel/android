package com.easyhz.placeapp.domain.model.feed.comment.post

data class CommentState(
    val postComment: PostComment = PostComment(),
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val error: String? = null
)

// TODO: 로그인 처리 시 -> userId, nickname 처리 필요
data class PostComment(
    val userId: String = "user1",
    val content: String = "",
    val nickname: String = "user1"
)

fun CommentState.updateContent(value: String): CommentState {
    return copy(postComment = postComment.copy(content = value))
}
