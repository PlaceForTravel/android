package com.easyhz.placeapp.domain.repository.feed

import com.easyhz.placeapp.domain.model.feed.Feed
import retrofit2.Response

interface FeedRepository {
    suspend fun fetchFeedContents(page: Int): Response<Feed>
}