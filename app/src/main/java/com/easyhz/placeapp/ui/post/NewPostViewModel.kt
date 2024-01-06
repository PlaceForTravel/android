package com.easyhz.placeapp.ui.post

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easyhz.placeapp.domain.model.place.PlaceResponse
import com.easyhz.placeapp.domain.model.place.PlaceItem
import com.easyhz.placeapp.domain.model.post.Place
import com.easyhz.placeapp.domain.repository.place.PlaceRepository
import com.easyhz.placeapp.domain.model.gallery.Gallery
import com.easyhz.placeapp.data.dataSource.GalleryPagingSource
import com.easyhz.placeapp.data.dataSource.GalleryPagingSource.Companion.PAGE_SIZE
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import com.easyhz.placeapp.domain.model.feed.detail.toPlace
import com.easyhz.placeapp.domain.model.post.ModifyPost
import com.easyhz.placeapp.domain.model.post.PostState
import com.easyhz.placeapp.domain.model.post.update
import com.easyhz.placeapp.domain.repository.feed.FeedRepository
import com.easyhz.placeapp.domain.repository.gallery.ImageRepository
import com.easyhz.placeapp.util.toAddress
import com.easyhz.placeapp.util.toLatLng
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
    private val placeRepository: PlaceRepository,
    private val feedRepository: FeedRepository
) : ViewModel() {

    private val _imageList = MutableStateFlow<PagingData<Gallery>>(PagingData.empty())
    val imageList: StateFlow<PagingData<Gallery>>
        get() = _imageList.asStateFlow()

    private var _placeList = mutableStateOf<PlaceResponse?>(null)
    val placeList: State<PlaceResponse?>
        get() = _placeList

    private val _selectedImageList = mutableStateListOf<Gallery>()
    val selectedImageList: SnapshotStateList<Gallery>
        get() = _selectedImageList

    var postState by mutableStateOf(PostState())

    private val _currentImage = mutableStateOf<Gallery?>(null)
    val currentImage: State<Gallery?>
        get() = _currentImage

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
        try {
            placeRepository.getPlaces(query, display, start, sort).let { response ->
                if(response.isSuccessful) {
                    _placeList.value = response.body()
                } else {
                    Log.e(":: ${this::class.java.simpleName}", "getPlaces Error : ${response.code()}")
                }
            }
        } catch (e: Exception) {
            postState = postState.copy(error = e.localizedMessage)
            Log.d(this::class.java.simpleName, "getPlaces error : $e")
        }
    }

    fun writePost() = viewModelScope.launch {
        try {
            postState = postState.copy(isLoading = true)
            feedRepository.writePost(postState.post, selectedImageList) { isSuccessful ->
                postState = if (isSuccessful) {
                    postState.copy(onSuccess = true)
                } else {
                    postState.copy(onSuccess = false)
                }
            }
        } catch (e: Exception) {
            postState = postState.copy(error = e.localizedMessage)
            Log.d(this::class.java.simpleName, "writePost error : $e")
        } finally {
            postState = postState.copy(isLoading = false)
        }
    }

    fun modifyPost(id: Int) = viewModelScope.launch {
        val item = ModifyPost(content = postState.post.content, userId = postState.post.userId, nickname = postState.post.nickname)
        try {
            postState = postState.copy(isLoading = true)
            feedRepository.modifyPost(id, item) { isSuccessful ->
                postState = postState.copy(onSuccess = isSuccessful)
            }
        } catch (e: Exception) {
            postState = postState.copy(error = e.localizedMessage)
            Log.d(this::class.java.simpleName, "modifyPost error : $e")
        } finally {
            postState = postState.copy(isLoading = false)
        }
    }

    fun setTextContent(value: String) {
        postState = postState.update(content = value)
    }

    fun manageSelectImage(id: Long, image: Gallery) {
        if (_selectedImageList.contains(image)) {
            removedSelectedImage(id, image)
            setCurrentImage()
        } else if (_selectedImageList.size < MAX_SELECT_COUNT) {
            addSelectedImage(id, image)
            setCurrentImage(image)
        }
    }

    fun initPlaces(placeBorderDefault: Color) {
        postState = postState.update(
            places = selectedImageList.mapIndexed { index, item ->
                Place(
                    placeName = null,
                    latitude = null,
                    longitude = null,
                    address = null,
                    imageFile = item.path,
                    imageName = item.name,
                    imgIndex = index,
                    placeBorderColor = placeBorderDefault
                )
            }
        )
    }

    fun initCityName() {
        postState = postState.update(cityName = "")
    }

    fun initError() {
        postState = postState.copy(error = null)
    }

    fun setPlaceList(value: PlaceResponse?) {
        _placeList.value = value
    }

    fun setPlaces(index: Int, placeItem: PlaceItem, placeBorderDefault: Color) {
        postState.post.places[index].apply {
            placeName = placeItem.title.withoutHTML()
            address = placeItem.roadAddress.ifEmpty { placeItem.roadAddress }
            longitude = (placeItem.mapx * POSITION_FORMAT).toLatLng(LAT_LNG_SCALE)
            latitude = (placeItem.mapy * POSITION_FORMAT).toLatLng(LAT_LNG_SCALE)
            placeBorderColor = placeBorderDefault
        }
        setPlaceList(null)
    }

    fun hasEqualCity(placeItem: PlaceItem, page: Int): Boolean {
        val address = placeItem.address.toAddress().ifEmpty { placeItem.roadAddress.toAddress() }
        if (isInitPlaceSelect()) return true
        if (postState.post.places[page].address != null && isFirstEdit()) return true
        return postState.post.places.any {
            it.address?.toAddress() ==  address
        }
    }

    fun setCityName(item: PlaceItem) {
        postState = postState.update(cityName = item.address.toAddress().ifEmpty { item.roadAddress.toAddress() })
    }

    fun setIsEqualCity(value: Boolean) {
        postState = postState.copy(isEqualCity = value)
    }

    fun onNextClick(
        placeBorderError: Color,
        animateScrollToPage: (Int) -> Unit,
    ) {
        if (hasAllPlaces()) writePost()
        else setUnselect(animateScrollToPage, placeBorderError)
    }

    fun setPostState(feedDetail: FeedDetail, placeBorderDefault: Color) {
        postState = postState.update(
            content = feedDetail.content,
            cityName = feedDetail.cityName,
            userId = feedDetail.userId,
            nickname = feedDetail.nickname,
            places = set(feedDetail, placeBorderDefault)
        ).copy(isEqualCity = true)
    }

    private fun set(feedDetail: FeedDetail, placeBorderDefault: Color) =
        feedDetail.placeImages.mapIndexed { index, placeImagesItem ->
            placeImagesItem.toPlace(index, placeBorderDefault)
        }

    private fun setPlaceBorder(placeBorderError:Color) {
        postState.unSelected.forEach { index ->
            postState.post.places[index].placeBorderColor = placeBorderError
        }
    }

    private fun addSelectedImage(id: Long, image: Gallery) = _selectedImageList.add(image)

    private fun removedSelectedImage(id: Long, image: Gallery) = _selectedImageList.remove(image)

    private fun findUnselectedPlace() {
        postState = postState.copy(unSelected = postState.post.places.indices.filter { listOfNullItems(postState.post.places[it]).isEmpty() })
    }

    private fun setUnselect(animateScrollToPage: (Int) -> Unit, placeBorderError: Color) {
        findUnselectedPlace()
        if (postState.unSelected.isNotEmpty()) {
            setPlaceBorder(placeBorderError)
            animateScrollToPage(postState.unSelected.first())
        }
    }

    private fun isInitPlaceSelect() : Boolean  = postState.post.places.all {
        listOfNullItems(it).isEmpty()
    }

    private fun isFirstEdit() : Boolean = postState.post.places.filter {
        listOfNullItems(it).isNotEmpty()
    }.size == 1

    private fun hasAllPlaces() : Boolean = postState.post.places.all {
        listOfNullItems(it).size == 4
    }

    private fun listOfNullItems(item: Place) = listOfNotNull(item.placeName, item.address, item.latitude, item.longitude)

    private fun setCurrentImage(image: Gallery? = _selectedImageList.lastOrNull()) {
        _currentImage.value = image
    }


    companion object {
        const val MAX_SELECT_COUNT = 2
        const val POSITION_FORMAT = (1 / 10_000_000.0)
        const val LAT_LNG_SCALE = 7
    }
}