package com.easyhz.placeapp.domain.model.bookmark.place

import com.easyhz.placeapp.ui.component.map.LatLngType

data class SavedPlaceItem(
    val address: String,
    val category: String,
    val latitude: Double,
    val longitude: Double,
    val placeId: Int,
    val placeName: String
) {
    companion object {
        fun List<SavedPlaceItem>.toLatLngType(): List<LatLngType> = this.map { LatLngType(it.latitude, it.longitude) }
    }
}