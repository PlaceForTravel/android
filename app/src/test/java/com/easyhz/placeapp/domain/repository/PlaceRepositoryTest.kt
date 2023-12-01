package com.easyhz.placeapp.domain.repository

import com.easyhz.placeapp.api.PlaceService
import com.easyhz.placeapp.domain.model.place.PlaceResponse
import com.easyhz.placeapp.domain.repository.place.PlaceRepositoryImpl
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`


class PlaceRepositoryTest {

    private var placeService: PlaceService = mock(PlaceService::class.java)
    private var repository = PlaceRepositoryImpl(placeService)
    @Test
    fun `Map getPlaces`() = runBlocking {
        val query = ""
        val display = 5
        val start = 1
        val sort = "random"
        `when`(placeService.getPlaces(query, display, start, sort))
            .thenReturn(
                Response.success(PlaceResponse(5, placeItems = listOf(), "1", 1, 5))
            )

        val result = repository.getPlaces(query, display, start, sort)
        assert(result.isSuccessful)
    }
}