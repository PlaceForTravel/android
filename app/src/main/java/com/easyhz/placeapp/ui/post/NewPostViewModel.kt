package com.easyhz.placeapp.ui.post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easyhz.placeapp.gallery.Gallery
import com.easyhz.placeapp.gallery.GalleryPagingSource
import com.easyhz.placeapp.gallery.GalleryPagingSource.Companion.PAGE_SIZE
import com.easyhz.placeapp.repository.gallery.ImageRepository
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
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _imageList = MutableStateFlow<PagingData<Gallery>>(PagingData.empty())
    val imageList: StateFlow<PagingData<Gallery>>
        get() = _imageList.asStateFlow()

    private val _selectedImageList = mutableStateListOf<Gallery>()
    val selectedImageList: SnapshotStateList<Gallery>
        get() = _selectedImageList

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

    fun setCurrentImage(image: Gallery? = _selectedImageList.lastOrNull()) {
        _currentImage.value = image
    }

    private fun setIsOver(value: Boolean) {
        _isOver.value = value
    }

    companion object {
        const val MAX_SELECT_COUNT = 2
    }
}