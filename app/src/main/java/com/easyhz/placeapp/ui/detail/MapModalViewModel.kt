package com.easyhz.placeapp.ui.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.easyhz.placeapp.domain.repository.feed.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapModalViewModel
@Inject constructor(
    private val feedRepository: FeedRepository
): ViewModel() {

    var isShowModal by mutableStateOf(false)

    private val _isViewAll = mutableStateOf(false)
    val isViewAll: State<Boolean>
        get() = _isViewAll

    fun setIsViewAll(value: Boolean) {
        _isViewAll.value = value
    }

}