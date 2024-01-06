package com.easyhz.placeapp.util

import org.junit.Test

class TimeFormatterTest {

    @Test
    fun testFormatTime() {
        val formattedTime = TimeFormatter.formatTime("2023-11-29T16:10:35.756476")
        assert(formattedTime.isNotEmpty())
    }
}