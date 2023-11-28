package com.easyhz.placeapp.domain.repository.place

import com.easyhz.placeapp.api.PlaceService
import com.easyhz.placeapp.domain.model.place.PlaceResponse
import retrofit2.Response
import javax.inject.Inject

class PlaceRepositoryImpl
@Inject constructor(
    private val placeService: PlaceService
): PlaceRepository {
    override suspend fun getPlaces(
        query: String,
        display: Int,
        start: Int,
        sort: String
    ): Response<PlaceResponse> = placeService.getPlaces(query, display, start, sort)
}