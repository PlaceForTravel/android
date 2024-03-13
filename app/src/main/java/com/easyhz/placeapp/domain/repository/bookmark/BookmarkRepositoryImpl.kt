package com.easyhz.placeapp.domain.repository.bookmark

import com.easyhz.placeapp.api.BookmarkService
import com.easyhz.placeapp.domain.model.bookmark.place.SavedPlaceItem
import com.easyhz.placeapp.domain.model.feed.Feed
import retrofit2.Response
import javax.inject.Inject

class BookmarkRepositoryImpl
@Inject constructor(
    private val bookmarkService: BookmarkService
):BookmarkRepository {
    override suspend fun fetchSavedPost(page: Int, userId: String): Response<Feed> = bookmarkService.getSavedPost(page, userId)

    override suspend fun fetchCityList(userId: String): Response<List<String>> = bookmarkService.getCity(userId)

    override suspend fun fetchPlaceList(userId: String): Response<List<SavedPlaceItem>> = bookmarkService.getSavedPlaces(userId)
}