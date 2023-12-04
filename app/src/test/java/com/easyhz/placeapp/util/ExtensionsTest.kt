package com.easyhz.placeapp.util

import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.Assertions.*


class ExtensionsTest {

    val POSITION_FORMAT = (1 / 10_000_000.0)
    val LAT_LNG_SCALE = 7
    @Test
    fun `Category Format Test`() = runTest {
        val mockValue = "서비스,산업>IT서비스"
        val expectedValue = "IT서비스"
        assertEquals(mockValue.toBasicCategory(), expectedValue)
    }

    @Test
    fun `Int to LatLng Format Test`() = runTest {
        val mockValue = 1271103930 * POSITION_FORMAT
        val expectedValue = 127.1103930
        assertEquals(mockValue.toLatLng(LAT_LNG_SCALE), expectedValue)
    }

    @Test
    fun `String to Address Format Test`() = runTest {
        val mockValue = "경기도 성남시 분당구 백현동 532"
        val expectedValue = "경기도 성남시"
        assertEquals(mockValue.toAddress(), expectedValue)
    }

    @Test
    fun `String to RoadAddress Format Test`() = runTest {
        val mockValue = "경기도 성남시 분당구 판교역로 166"
        val expectedValue = "경기도 성남시"
        assertEquals(mockValue.toAddress(), expectedValue)
    }
}