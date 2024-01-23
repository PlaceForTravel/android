package com.easyhz.placeapp.domain.repository.bookmark

import com.easyhz.placeapp.api.BookmarkService
import com.easyhz.placeapp.domain.model.feed.Feed
import com.easyhz.placeapp.domain.model.user.User
import retrofit2.Response
import javax.inject.Inject

class BookmarkRepositoryImpl
@Inject constructor(
    private val bookmarkService: BookmarkService
):BookmarkRepository {
    override suspend fun fetchSavedPost(page: Int, userId: String): Response<Feed> = bookmarkService.getSavedPost(page, userId)
}