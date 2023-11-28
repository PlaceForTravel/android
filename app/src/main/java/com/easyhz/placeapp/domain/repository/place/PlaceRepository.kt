package com.easyhz.placeapp.domain.repository.place

import com.easyhz.placeapp.domain.model.place.PlaceResponse
import retrofit2.Response

interface PlaceRepository {

    suspend fun getPlaces(
        query: String,
        display: Int,
        start: Int,
        sort: String
    ): Response<PlaceResponse>
}