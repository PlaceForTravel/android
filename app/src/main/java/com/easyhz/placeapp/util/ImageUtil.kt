package com.easyhz.placeapp.util

import android.content.Context
import coil.request.ImageRequest

fun getImageRequestDefault(data: Any?, context: Context) =
    ImageRequest.Builder(context)
        .data(data)
        .crossfade(true)
        .build()
