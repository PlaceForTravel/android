package com.easyhz.placeapp.domain.model.post

import androidx.compose.ui.graphics.Color
data class Place(
    var placeName: String?,
    var latitude: Double?,
    var longitude: Double?,
    var address: String?,
    var imageFile: String,
    val imageName: String,
    var placeBorderColor: Color,
)