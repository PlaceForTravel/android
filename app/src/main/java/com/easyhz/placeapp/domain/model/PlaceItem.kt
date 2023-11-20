package com.easyhz.placeapp.domain.model

data class PlaceItem(
    val address: String,
    val category: String,
    val description: String,
    val link: String,
    val mapx: String,
    val mapy: String,
    val roadAddress: String,
    val telephone: String,
    val title: String
)