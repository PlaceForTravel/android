package com.easyhz.placeapp.ui.home.bookmark

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyhz.placeapp.domain.model.bookmark.place.SavedPlaceItem
import com.easyhz.placeapp.domain.model.user.UserManager
import com.easyhz.placeapp.domain.repository.bookmark.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceBookmarkViewModel
@Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
): ViewModel() {

    var cityList by mutableStateOf<List<String>>(emptyList())

    var placeList by mutableStateOf<List<SavedPlaceItem>>(emptyList())

    fun fetchCityList() = viewModelScope.launch {
        UserManager.user?.userId?.let { userId ->
            val response = bookmarkRepository.fetchCityList(userId)
            if(response.isSuccessful) {
                response.body()?.let { res ->
                    cityList = res
                }
            }
        }
    }

    fun fetchPlaceList() = viewModelScope.launch {
        UserManager.user?.userId?.let {userId ->
            val response = bookmarkRepository.fetchPlaceList(userId)
            if (response.isSuccessful) {
                response.body()?.let { res ->
                    placeList = res
                }
            }
        }
    }
}