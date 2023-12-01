package com.easyhz.placeapp.domain.repository.feed

import com.easyhz.placeapp.domain.model.feed.Feed
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import retrofit2.Response

interface FeedRepository {
    suspend fun fetchFeedContents(page: Int): Response<Feed>

    suspend fun fetchFeedDetail(id: Int): Response<FeedDetail>
}