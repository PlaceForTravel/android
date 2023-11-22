package com.easyhz.placeapp.domain.model.place

data class PlaceItem(
    val address: String,
    val category: String,
    val description: String,
    val link: String,
    val mapx: Int,
    val mapy: Int,
    val roadAddress: String,
    val telephone: String,
    val title: String
)