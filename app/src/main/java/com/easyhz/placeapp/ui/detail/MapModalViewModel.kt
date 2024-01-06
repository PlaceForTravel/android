package com.easyhz.placeapp.ui.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyhz.placeapp.domain.model.feed.SaveState
import com.easyhz.placeapp.domain.repository.feed.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapModalViewModel
@Inject constructor(
    private val feedRepository: FeedRepository
): ViewModel() {

    var isShowModal by mutableStateOf(false)
    var savePlaceState by mutableStateOf(SaveState())

    private val _isViewAll = mutableStateOf(false)
    val isViewAll: State<Boolean>
        get() = _isViewAll

    fun setIsViewAll(value: Boolean) {
        _isViewAll.value = value
    }

    fun savePlace(boardId: Int) = viewModelScope.launch {
        try {
            feedRepository.savePlace(boardId, savePlaceState.userInfo) { isSuccessful ->
                savePlaceState = savePlaceState.copy(isSuccessful = isSuccessful)
            }
        } catch (e: Exception) {
            savePlaceState = savePlaceState.copy(error = e.localizedMessage)
        }
    }
}