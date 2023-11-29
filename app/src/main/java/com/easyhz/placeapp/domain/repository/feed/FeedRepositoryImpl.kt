package com.easyhz.placeapp.domain.repository.feed

import com.easyhz.placeapp.api.FeedService
import com.easyhz.placeapp.domain.model.feed.Feed
import retrofit2.Response
import javax.inject.Inject

class FeedRepositoryImpl
@Inject constructor(
    private val feedService: FeedService
):FeedRepository {
    override suspend fun fetchFeedContents(page: Int): Response<Feed> = feedService.getFeed(page)
}