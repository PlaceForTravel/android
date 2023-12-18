package com.easyhz.placeapp.ui.detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easyhz.placeapp.data.dataSource.CommentPagingSource
import com.easyhz.placeapp.data.dataSource.CommentPagingSource.Companion.PAGE_SIZE
import com.easyhz.placeapp.domain.model.feed.SaveState
import com.easyhz.placeapp.domain.model.feed.comment.CommentContent
import com.easyhz.placeapp.domain.model.feed.comment.write.CommentState
import com.easyhz.placeapp.domain.model.feed.comment.write.updateContent
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import com.easyhz.placeapp.domain.model.feed.detail.PlaceImagesItem
import com.easyhz.placeapp.domain.repository.feed.FeedRepository
import com.easyhz.placeapp.ui.component.map.LatLngType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _comments = MutableStateFlow<PagingData<CommentContent>>(PagingData.empty())
    val comments: StateFlow<PagingData<CommentContent>>
        get() = _comments.asStateFlow()

    var commentState by mutableStateOf(CommentState())

    private val _placeImagesItem = mutableStateOf<PlaceImagesItem?>(null)
    val placeImagesItem: State<PlaceImagesItem?>
        get() = _placeImagesItem

    private val _allPlaces = mutableListOf<LatLngType>()
    val allPlaces: MutableList<LatLngType>
        get() = _allPlaces

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean>
        get() = _isLoading

    var savePostState by mutableStateOf(SaveState())


    fun fetchFeedDetail(id: Int) = viewModelScope.launch {
        setIsLoading(true)
        val response = feedRepository.fetchFeedDetail(id)
        if(response.isSuccessful) {
            _feedDetail.value = response.body()
            getAllPlaceLatLng()
        } else {
            Log.e(":: ${this::class.java.simpleName}", "fetchFeedDetail Error : ${response.code()}")
        }
        setIsLoading(false)
    }

    fun fetchComments(id: Int) = viewModelScope.launch {
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                CommentPagingSource(
                    feedRepository = feedRepository,
                    id = id
                )
            }
        ).flow.cachedIn(viewModelScope).collectLatest {
            _comments.value = it
        }
    }

    fun saveComment(id: Int) = viewModelScope.launch {
        try {
            commentState = commentState.copy(isLoading = true)
            feedRepository.writeComment(id, commentState.postComment) { isSuccessful ->
                commentState = if (isSuccessful) {
                    Log.d(this::class.java.simpleName, "Success")
                    setCommentText("")
                    commentState.copy(isSuccessful = true)
                } else {
                    Log.d(this::class.java.simpleName, "Fail")
                    commentState.copy(isSuccessful = false)
                }
            }
        } catch (e: Exception) {
            commentState = commentState.copy(error = e.localizedMessage)
        } finally {
            commentState = commentState.copy(isLoading = false)
        }
    }
    fun resetCommentState() {
        commentState = commentState.copy(
            isLoading = false,
            isSuccessful = false,
            error = null
        )
    }
    fun setPlaceImagesItem(item: PlaceImagesItem) {
        _placeImagesItem.value = item
    }

    fun setCommentText(value: String) {
        commentState = commentState.updateContent(value)
    }

    /**
     * 게시물 저장
     **/
    fun savePost(boardId: Int) = viewModelScope.launch {
        // TODO: 유저 아이디 추가 필요
        try {
            feedRepository.savePost(boardId, savePostState.userInfo) { isSuccessful ->
                savePostState = savePostState.copy(isSuccessful = isSuccessful)
                if (isSuccessful) resetFeedDetail()
            }
        } catch (e: Exception) {
            savePostState = savePostState.copy(error = e.localizedMessage)
        }
    }

    private fun resetFeedDetail() {
        _feedDetail.value = _feedDetail.value?.let {
            it.copy(likeCount = it.likeCount + 1)
        }
    }


    private fun getAllPlaceLatLng() {
        _allPlaces.addAll(feedDetail.value?.placeImages?.map { LatLngType(it.latitude, it.longitude) } ?: emptyList())
    }

    private fun setIsLoading(value: Boolean) {
        _isLoading.value = value
    }
}