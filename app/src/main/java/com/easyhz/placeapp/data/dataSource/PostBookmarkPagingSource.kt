package com.easyhz.placeapp.data.dataSource

import com.easyhz.placeapp.domain.model.feed.Content
import com.easyhz.placeapp.domain.model.user.UserManager
import com.easyhz.placeapp.domain.repository.bookmark.BookmarkRepository

class PostBookmarkPagingSource(
    private val bookmarkRepository: BookmarkRepository,
): BasePagingSource<Content>() {
    override suspend fun fetchData(page: Int): List<Content>? {
        val data = bookmarkRepository.fetchSavedPost(page, UserManager.user?.userId ?: "")
        if (data.isSuccessful) {
            return data.body()?.content ?: emptyList()
        }
        return null
    }

    companion object {
        const val PAGE_SIZE = 5
    }
}