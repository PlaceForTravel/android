package com.easyhz.placeapp.ui.post

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easyhz.placeapp.domain.model.place.PlaceResponse
import com.easyhz.placeapp.domain.model.place.PlaceItem
import com.easyhz.placeapp.domain.model.post.Place
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
) : ViewModel() {

    private val _imageList = MutableStateFlow<PagingData<Gallery>>(PagingData.empty())
    val imageList: StateFlow<PagingData<Gallery>>
        get() = _imageList.asStateFlow()

    private var _placeList = mutableStateOf<PlaceResponse?>(null)
    val placeList: State<PlaceResponse?>
        get() = _placeList

    private var _selectedImagePlaceList = mutableStateListOf<Place>()
    val selectedImagePlaceList: SnapshotStateList<Place>
        get() = _selectedImagePlaceList

    private val _selectedImageList = mutableStateListOf<Gallery>()
    val selectedImageList: SnapshotStateList<Gallery>
        get() = _selectedImageList

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
        _selectedImagePlaceList = selectedImageList.map { item ->
            Place(
                placeName = null,
                latitude = null,
                longitude = null,
                address = null,
                imageFile = item.path,
                imageName = item.name,
            )
        }.toMutableStateList()
    }

    fun setPlaceList(index: Int, placeItem: PlaceItem) {
        _selectedImagePlaceList[index].apply {
            placeName = placeItem.title.withoutHTML()
            address = placeItem.roadAddress.ifEmpty { placeItem.roadAddress }
        }
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