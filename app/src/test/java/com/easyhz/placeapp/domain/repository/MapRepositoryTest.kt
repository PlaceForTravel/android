package com.easyhz.placeapp.domain.repository

import com.easyhz.placeapp.api.MapService
import com.easyhz.placeapp.domain.model.MapResponse
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`


class MapRepositoryTest {

    private var mapService: MapService = mock(MapService::class.java)
    private var repository = MapRepositoryImpl(mapService)
    @Test
    fun `Map getPlaces`() = runBlocking {
        val query = ""
        val display = 5
        val start = 1
        val sort = "random"
        `when`(mapService.getPlaces(query, display, start, sort))
            .thenReturn(
                Response.success(MapResponse(5, placeItems = listOf(), "1", 1, 5))
            )

        val result = repository.getPlaces(query, display, start, sort)

        assert(result.isSuccessful)

    }
}