package com.easyhz.placeapp.domain.repository.bookmark

import com.easyhz.placeapp.domain.model.bookmark.place.SavedPlaceItem
import com.easyhz.placeapp.domain.model.feed.Feed
import retrofit2.Response

interface BookmarkRepository {
    suspend fun fetchSavedPost(page: Int, userId: String): Response<Feed>

    suspend fun fetchCityList(userId: String): Response<List<String>>

    suspend fun fetchPlaceList(userId: String): Response<List<SavedPlaceItem>>
}