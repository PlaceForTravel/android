package com.easyhz.placeapp.ui.detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import com.easyhz.placeapp.domain.repository.feed.FeedRepository
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

    fun fetchFeedDetail(id: Int) = viewModelScope.launch {
        val response = feedRepository.fetchFeedDetail(id)
        if(response.isSuccessful) {
            _feedDetail.value = response.body()
        } else {
            Log.e(":: ${this::class.java.simpleName}", "fetchFeedDetail Error : ${response.code()}")
        }
    }
}