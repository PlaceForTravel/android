package com.easyhz.placeapp.domain.repository.feed

import com.easyhz.placeapp.api.FeedService
import com.easyhz.placeapp.domain.model.feed.Feed
import com.easyhz.placeapp.domain.model.feed.comment.Comment
import com.easyhz.placeapp.domain.model.feed.comment.post.PostComment
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class FeedRepositoryImpl
@Inject constructor(
    private val feedService: FeedService
):FeedRepository {
    override suspend fun fetchFeedContents(page: Int): Response<Feed> = feedService.getFeed(page)

    override suspend fun fetchFeedDetail(id: Int): Response<FeedDetail> = feedService.getFeedDetail(id = id)

    override suspend fun fetchComments(id: Int, page: Int): Response<Comment> = feedService.getComments(id = id, page = page)

    override suspend fun saveComment(
        id: Int,
        comment: PostComment,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        val response = feedService.saveComments(id = id, comment =  comment)
        if (response.isSuccessful) {
            onComplete(true)
        } else {
            onComplete(false)
        }
    }
}