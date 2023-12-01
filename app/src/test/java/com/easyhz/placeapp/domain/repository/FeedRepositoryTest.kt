package com.easyhz.placeapp.domain.repository

import com.easyhz.placeapp.api.FeedService
import com.easyhz.placeapp.domain.model.feed.Content
import com.easyhz.placeapp.domain.model.feed.Feed
import com.easyhz.placeapp.domain.model.feed.Pageable
import com.easyhz.placeapp.domain.model.feed.SortX
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import com.easyhz.placeapp.domain.model.feed.detail.PlaceResponseDTOS
import com.easyhz.placeapp.domain.repository.feed.FeedRepositoryImpl
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class FeedRepositoryTest {

    private var feedService: FeedService = mock(FeedService::class.java)
    private var repository = FeedRepositoryImpl(feedService)

    @Test
    fun `fetch Feed Contents`() = runBlocking {
        `when`(feedService.getFeed(1)).thenReturn(
            Response.success(
                Feed(
                    listOf(Content(1, "서울", "", listOf(), 0,"", "", "", "", "", "")),
                false, false, false, 0, 0, Pageable(0, 0, 0, true, SortX(true, true, true), false), 5, SortX(true, true, true), 0, 0
                )
            )
        )

        val result = repository.fetchFeedContents(1)
        assert(result.isSuccessful)
    }

    @Test
    fun `fetch Feed Detail`() = runBlocking {
        `when`(feedService.getFeedDetail(1)).thenReturn(
            Response.success(
                FeedDetail(1, "서울특별시 종로구", "여기 정말 좋아요", "", 0, "", "user1",
                    listOf(PlaceResponseDTOS("", 0, "", 0.0, 0.0, 0, "")), "", "")
            )
        )

        val result = repository.fetchFeedDetail(1)
        assert(result.isSuccessful)
    }
}