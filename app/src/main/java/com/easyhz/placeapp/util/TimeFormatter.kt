package com.easyhz.placeapp.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

const val ORIGINAL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
const val FORMAT = "yyyy.MM.dd HH:mm:ss"
object TimeFormatter {
    private val current = Date()

    @SuppressLint("SimpleDateFormat")
    private val format = SimpleDateFormat(ORIGINAL_FORMAT)

    private val cachedFormats = mutableMapOf<String, String>()

    private lateinit var date: Date
    private var diff = 0L

    private val seconds: Int
        get() = calDiff(TimeUnit.SECONDS)

    private val minutes: Int
        get() = calDiff(TimeUnit.MINUTES)

    private val hours: Int
        get() = calDiff(TimeUnit.HOURS)

    private val days: Int
        get() = calDiff(TimeUnit.DAYS)
    @SuppressLint("SimpleDateFormat")
    fun formatTime(regDate: String): String {

        if (cachedFormats.containsKey(regDate)) {
            return cachedFormats[regDate]!!
        }

        date = format.parse(regDate) ?: current
        diff = current.time - date.time
        return "${
            when {
                seconds < 60 -> "방금"
                minutes < 60 -> "$minutes 분"
                hours < 24 -> "$hours 시간"
                days <= 14 -> "$days 일"
                else -> SimpleDateFormat(FORMAT).format(date)
            }
        } 전"
    }

    private fun calDiff(unit: TimeUnit): Int {
        return unit.convert(diff, TimeUnit.MILLISECONDS).toInt()
    }

}