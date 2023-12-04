package com.easyhz.placeapp.domain.model.place

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    val display: Int,
    @SerializedName("items")
    val placeItems: List<PlaceItem>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
)