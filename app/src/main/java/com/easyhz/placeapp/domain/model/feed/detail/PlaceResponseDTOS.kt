package com.easyhz.placeapp.domain.model.feed.detail

data class PlaceResponseDTOS(
    val address: String,
    val boardPlaceid: Int,
    val imgUrl: String,
    val latitude: Double,
    val longitude: Double,
    val placeId: Int,
    val placeName: String
)