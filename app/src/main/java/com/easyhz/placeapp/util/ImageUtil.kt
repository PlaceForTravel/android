package com.easyhz.placeapp.util

import android.content.Context
import coil.request.ImageRequest

fun getImageRequestDefault(data: Any?, context: Context) =
    ImageRequest.Builder(context)
        .data(data)
        .memoryCacheKey(key = data.toString())
        .crossfade(true)
        .crossfade(500)
        .build()
