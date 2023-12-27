package com.easyhz.placeapp.util

import android.content.Context
import coil.request.ImageRequest

fun getImageRequestDefault(data: Any?, context: Context, crossFade: Int = 500) =
    ImageRequest.Builder(context)
        .data(data)
        .memoryCacheKey(key = data.toString())
        .crossfade(true)
        .crossfade(crossFade)
        .build()
