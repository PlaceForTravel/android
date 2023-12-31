package com.easyhz.placeapp.api

import com.easyhz.placeapp.domain.model.place.PlaceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("/v1/search/local.json")
    suspend fun getPlaces(
        @Query("query") query: String,
        @Query("display") display: Int = 5,
        @Query("start") start: Int = 1,
        @Query("sort") sort: String = "random"
    ) : Response<PlaceResponse>
}