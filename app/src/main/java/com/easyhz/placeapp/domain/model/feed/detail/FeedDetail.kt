package com.easyhz.placeapp.domain.model.feed.detail

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedDetail(
    val boardId: Int,
    val cityName: String,
    val content: String,
    val likeCount: Int,
    val nickname: String,
    @SerializedName("placeResponseDTOS")
    val placeImages: List<PlaceImagesItem>,
    val regDate: String,
    val modifiedDate: String? = null,
    val deletedDate: String? = null,
    val userId: String,
    var like: Boolean
) : Parcelable