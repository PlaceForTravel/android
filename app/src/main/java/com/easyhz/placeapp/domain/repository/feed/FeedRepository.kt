package com.easyhz.placeapp.domain.repository.feed

import com.easyhz.placeapp.domain.model.feed.Feed
import com.easyhz.placeapp.domain.model.feed.UserInfo
import com.easyhz.placeapp.domain.model.feed.comment.Comment
import com.easyhz.placeapp.domain.model.feed.comment.write.PostComment
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import com.easyhz.placeapp.domain.model.gallery.Gallery
import com.easyhz.placeapp.domain.model.post.ModifyPost
import com.easyhz.placeapp.domain.model.post.Post
import retrofit2.Response

interface FeedRepository {
    suspend fun fetchFeedContents(page: Int, userId: String): Response<Feed>

    suspend fun fetchFeedDetail(id: Int, userId: String): Response<FeedDetail>

    suspend fun fetchComments(id: Int, page: Int): Response<Comment>

    suspend fun writePost(
        post: Post,
        images: List<Gallery>,
        onComplete: (Boolean) -> Unit
    )

    suspend fun deletePost(
        id: Int,
        onComplete: (Boolean) -> Unit
    )

    suspend fun modifyPost(
        id: Int,
        content: ModifyPost,
        onComplete: (Boolean) -> Unit
    )

    suspend fun writeComment(
        id: Int,
        comment: PostComment,
        onComplete: (Boolean) -> Unit
    )

    suspend fun savePost(
        boardId: Int,
        userInfo: UserInfo,
        onComplete: (Boolean) -> Unit
    )

    suspend fun savePlace(
        boardId: Int,
        userInfo: UserInfo,
        onComplete: (Boolean) -> Unit
    )
}