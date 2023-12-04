package com.easyhz.placeapp.data.dataSource

import com.easyhz.placeapp.domain.model.feed.Content
import com.easyhz.placeapp.domain.repository.feed.FeedRepository

/**
 * https://developer.android.com/reference/kotlin/androidx/paging/PagingSource
 *
 * CommentPagingSource 와 비슷 해서 추상 클래스 BasePagingSource 의 상속을 받는다.
 * @since 2023.12.04
 *
 */
class FeedPagingSource(
    private val feedRepository: FeedRepository,
):BasePagingSource<Content>() {
    override suspend fun fetchData(page: Int): List<Content>? {
        val data = feedRepository.fetchFeedContents(page = page)
        if (data.isSuccessful) {
            return data.body()?.content ?: listOf()
        }
        return null
    }
    companion object {
        const val PAGE_SIZE = 5
    }
}