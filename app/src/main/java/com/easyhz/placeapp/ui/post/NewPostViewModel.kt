package com.easyhz.placeapp.ui.post

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easyhz.placeapp.R
import com.easyhz.placeapp.domain.model.MapResponse
import com.easyhz.placeapp.domain.model.PlaceItem
import com.easyhz.placeapp.domain.repository.MapRepository
import com.easyhz.placeapp.gallery.Gallery
import com.easyhz.placeapp.gallery.GalleryPagingSource
import com.easyhz.placeapp.gallery.GalleryPagingSource.Companion.PAGE_SIZE
import com.easyhz.placeapp.domain.repository.gallery.ImageRepository
import com.easyhz.placeapp.util.withoutHTML
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPostViewModel
@Inject constructor(
    private val imageRepository: ImageRepository,
    private val mapRepository: MapRepository,
    private val application: Application = Application()
) : ViewModel() {

    private val _imageList = MutableStateFlow<PagingData<Gallery>>(PagingData.empty())
    val imageList: StateFlow<PagingData<Gallery>>
        get() = _imageList.asStateFlow()

    private var _placeList = mutableStateOf<MapResponse?>(null)
    val placeList: State<MapResponse?>
        get() = _placeList


    private val _selectedImageList = mutableStateListOf<Gallery>()
    val selectedImageList: SnapshotStateList<Gallery>
        get() = _selectedImageList

    private val _selectedPlaceList = mutableStateListOf<String>()
    val selectedPlaceList: SnapshotStateList<String>
        get() = _selectedPlaceList

    private val _textContent = mutableStateOf("")
    val textContent: State<String>
        get() = _textContent

    private val _currentImage = mutableStateOf<Gallery?>(null)
    val currentImage: State<Gallery?>
        get() = _currentImage

    private val _isOver = mutableStateOf(false)
    val isOver: State<Boolean>
        get() = _isOver

    fun getGalleryImages() = viewModelScope.launch {
        _imageList.value = PagingData.empty()
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                GalleryPagingSource(
                    imageRepository = imageRepository,
                    currentLocation = null
                )
            }
        ).flow.cachedIn(viewModelScope).collectLatest {
            _imageList.value = it
        }
    }

    fun getPlaces(query: String, display: Int, start: Int, sort: String) = viewModelScope.launch {
        mapRepository.getPlaces(query, display, start, sort).let {response ->
            if(response.isSuccessful) {
                _placeList.value = response.body()
            } else {
                Log.e(":: ${this::class.java.simpleName}", "getPlaces Error : ${response.code()}")
            }
        }
    }

    fun setTextContent(value: String) {
        _textContent.value = value
    }

    fun addSelectedImage(id: Long, image: Gallery) = _selectedImageList.add(image)

    fun removedSelectedImage(id: Long, image: Gallery) = _selectedImageList.remove(image)

    fun manageSelectImage(id: Long, image: Gallery) =
        if (_selectedImageList.contains(image)) {
            removedSelectedImage(id, image)
            setCurrentImage()
            setIsOver(false)
        } else if (_selectedImageList.size < MAX_SELECT_COUNT) {
            addSelectedImage(id, image)
            setCurrentImage(image)
        } else {
            setIsOver(true)
        }

    fun initPlaceList() {
        val init = getString(application, R.string.post_add_place)
        selectedImageList.forEach{ _ ->
            _selectedPlaceList.add(init)
        }
    }

    fun setPlaceList(index: Int, placeItem: PlaceItem) {
        _selectedPlaceList[index] = placeItem.title.withoutHTML()
        _placeList.value = null
    }

    private fun setCurrentImage(image: Gallery? = _selectedImageList.lastOrNull()) {
        _currentImage.value = image
    }

    private fun setIsOver(value: Boolean) {
        _isOver.value = value
    }

    companion object {
        const val MAX_SELECT_COUNT = 2
    }
}