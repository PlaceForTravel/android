package com.easyhz.placeapp.ui.detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import com.easyhz.placeapp.domain.model.feed.detail.PlaceImagesItem
import com.easyhz.placeapp.domain.repository.feed.FeedRepository
import com.easyhz.placeapp.ui.component.map.LatLngType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardDetailViewModel
@Inject constructor(
    private val feedRepository: FeedRepository
):ViewModel() {
    private val _feedDetail = mutableStateOf<FeedDetail?>(null)
    val feedDetail: State<FeedDetail?>
        get() = _feedDetail

    private val _placeImagesItem = mutableStateOf<PlaceImagesItem?>(null)
    val placeImagesItem: State<PlaceImagesItem?>
        get() = _placeImagesItem

    private val _allPlaces = mutableListOf<LatLngType>()
    val allPlaces: MutableList<LatLngType>
        get() = _allPlaces


    private val _isViewAll = mutableStateOf(false)
    val isViewAll: State<Boolean>
        get() = _isViewAll

    private val _commentText = mutableStateOf("")
    val commentText: State<String>
        get() = _commentText

    fun fetchFeedDetail(id: Int) = viewModelScope.launch {
        val response = feedRepository.fetchFeedDetail(id)
        if(response.isSuccessful) {
            _feedDetail.value = response.body()
            getAllPlaceLatLng()
        } else {
            Log.e(":: ${this::class.java.simpleName}", "fetchFeedDetail Error : ${response.code()}")
        }
    }

    fun setPlaceImagesItem(item: PlaceImagesItem) {
        _placeImagesItem.value = item
    }

    fun setIsViewAll() {
        _isViewAll.value = !_isViewAll.value
    }

    fun setCommentText(value: String) {
        _commentText.value = value
    }

    private fun getAllPlaceLatLng() {
        _allPlaces.addAll(feedDetail.value?.placeImages?.map { LatLngType(it.latitude, it.longitude) } ?: emptyList())
    }
}