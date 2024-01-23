package com.easyhz.placeapp.domain.model.feed.detail

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.easyhz.placeapp.domain.model.post.Place
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceImagesItem(
    val address: String,
    @SerializedName("boardPlaceid")
    val boardPlaceId: Int,
    val imgUrl: String,
    val latitude: Double,
    val longitude: Double,
    val placeId: Int,
    val placeName: String,
    val category: String,
) : Parcelable


fun PlaceImagesItem.toPlace(imgIndex: Int, placeBorderColor: Color): Place = Place(
    placeName = placeName,
    latitude = latitude,
    longitude = longitude,
    address = address,
    imageFile = imgUrl,
    imageName = imgUrl,
    imgIndex = imgIndex,
    placeBorderColor = placeBorderColor,
    category = category
)