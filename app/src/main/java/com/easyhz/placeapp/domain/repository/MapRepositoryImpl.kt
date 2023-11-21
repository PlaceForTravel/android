package com.easyhz.placeapp.domain.repository

import com.easyhz.placeapp.api.MapService
import com.easyhz.placeapp.domain.model.place.PlaceResponse
import retrofit2.Response
import javax.inject.Inject

class MapRepositoryImpl
@Inject constructor(
    private val mapService: MapService
): MapRepository {
    override suspend fun getPlaces(
        query: String,
        display: Int,
        start: Int,
        sort: String
    ): Response<PlaceResponse> = mapService.getPlaces(query, display, start, sort)
}