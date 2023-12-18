package com.easyhz.placeapp.domain.repository.feed

import com.easyhz.placeapp.domain.model.feed.Feed
import com.easyhz.placeapp.domain.model.feed.UserInfo
import com.easyhz.placeapp.domain.model.feed.comment.Comment
import com.easyhz.placeapp.domain.model.feed.comment.write.PostComment
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import retrofit2.Response

interface FeedRepository {
    suspend fun fetchFeedContents(page: Int): Response<Feed>

    suspend fun fetchFeedDetail(id: Int): Response<FeedDetail>

    suspend fun fetchComments(id: Int, page: Int): Response<Comment>

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
}