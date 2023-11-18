package com.easyhz.placeapp.domain.repository

import com.easyhz.placeapp.api.MapService
import com.easyhz.placeapp.domain.model.MapResponse
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
    ): Response<MapResponse> = mapService.getPlaces(query, display, start, sort)
}