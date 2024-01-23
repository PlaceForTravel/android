package com.easyhz.placeapp.domain.repository.bookmark

import com.easyhz.placeapp.domain.model.feed.Feed
import com.easyhz.placeapp.domain.model.user.User
import retrofit2.Response

interface BookmarkRepository {
    suspend fun fetchSavedPost(page: Int, user: User?): Response<Feed>
}