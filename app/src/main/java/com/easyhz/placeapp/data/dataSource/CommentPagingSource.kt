package com.easyhz.placeapp.data.dataSource

import com.easyhz.placeapp.domain.model.feed.comment.CommentContent
import com.easyhz.placeapp.domain.repository.feed.FeedRepository

/**
 * FeedPagingSource 와 비슷 해서 추상 클래스 BasePagingSource 의 상속을 받는다.
 * @since 2023.12.04
 */
class CommentPagingSource(
    private val feedRepository: FeedRepository,
    private val id: Int,
): BasePagingSource<CommentContent>() {
    override suspend fun fetchData(page: Int): List<CommentContent>? {
        val data = feedRepository.fetchComments(page = page, id = id)
        if (data.isSuccessful) {
            return data.body()?.content ?: emptyList()
        }
        return null
    }

    companion object {
        const val PAGE_SIZE = 10
    }

}