package com.easyhz.placeapp.domain.model.feed.detail

import com.google.gson.annotations.SerializedName

data class PlaceImagesItem(
    val address: String,
    @SerializedName("boardPlaceid")
    val boardPlaceId: Int,
    val imgUrl: String,
    val latitude: Double,
    val longitude: Double,
    val placeId: Int,
    val placeName: String
)