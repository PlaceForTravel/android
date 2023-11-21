package com.easyhz.placeapp.domain.repository

import com.easyhz.placeapp.domain.model.place.PlaceResponse
import retrofit2.Response

interface MapRepository {

    suspend fun getPlaces(
        query: String,
        display: Int,
        start: Int,
        sort: String
    ): Response<PlaceResponse>
}