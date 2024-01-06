package com.easyhz.placeapp.ui.home.feed

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.easyhz.placeapp.data.dataSource.FeedPagingSource
import com.easyhz.placeapp.data.dataSource.FeedPagingSource.Companion.PAGE_SIZE
import com.easyhz.placeapp.domain.model.feed.Content
import com.easyhz.placeapp.domain.model.feed.SaveState
import com.easyhz.placeapp.domain.model.feed.ScreenState
import com.easyhz.placeapp.domain.repository.feed.FeedRepository
import com.easyhz.placeapp.domain.repository.user.UserDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel
@Inject constructor(
    private val feedRepository: FeedRepository,
    private val dataStoreRepository: UserDataStoreRepository
): ViewModel() {
    var isFirstRun by mutableStateOf(true)
    var isShowDialog by mutableStateOf(true)

    var savePostState by mutableStateOf(SaveState())
    var screenState by mutableStateOf(ScreenState())

    var pager = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = {
            FeedPagingSource(
                feedRepository = feedRepository
            )
        }
    ).flow.cachedIn(viewModelScope)

    init {
        try {
            getIsFirstRun()
        } catch (e: Exception) {
            screenState = screenState.copy(error = e.localizedMessage)
            Log.d(this::class.java.simpleName, e.message.toString())
        }
    }


    fun refreshFeed(contents: LazyPagingItems<Content>) = viewModelScope.launch {
        screenState = screenState.copy(isRefreshing = true)
        delay(500)
        contents.refresh()
        screenState = screenState.copy(isRefreshing = false)
    }

    fun retryFeed(contents: LazyPagingItems<Content>) = viewModelScope.launch {
        screenState = screenState.copy(isLoading = true)
        delay(500)
        contents.retry()
        screenState = screenState.copy(isLoading = false)
    }

    /**
     * 게시물 저장
     **/
    fun savePost(boardId: Int, contents: LazyPagingItems<Content>) = viewModelScope.launch {
        // TODO: 유저 아이디 추가 필요
        try {
            feedRepository.savePost(boardId, savePostState.userInfo) { isSuccessful ->
                screenState = screenState.copy(isLoading = true)
                savePostState = savePostState.copy(isSuccessful = isSuccessful)
                if (isSuccessful) {
                    contents.itemSnapshotList
                        .filter { it?.boardId == boardId }
                        .forEach { it?.likeCount = it?.likeCount?.plus(1) ?: 0 }
                }
            }
        } catch (e: Exception) {
            savePostState = savePostState.copy(error = e.localizedMessage)
        } finally {
            screenState = screenState.copy(isLoading = false)
        }
    }

    fun getIsFirstRun() = viewModelScope.launch {
        dataStoreRepository.getIsFirstRun().collectLatest {
            isFirstRun = it
        }
    }

    fun setIsShowDialog(value: Boolean) {
        isShowDialog = value
    }
}