package com.easyhz.placeapp.ui.home.bookmark

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.easyhz.placeapp.data.dataSource.PostBookmarkPagingSource
import com.easyhz.placeapp.data.dataSource.PostBookmarkPagingSource.Companion.PAGE_SIZE
import com.easyhz.placeapp.domain.model.feed.Content
import com.easyhz.placeapp.domain.model.feed.ScreenState
import com.easyhz.placeapp.domain.model.user.User
import com.easyhz.placeapp.domain.repository.bookmark.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel
@Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
): ViewModel() {
    var screenState by mutableStateOf(ScreenState())

    var pager = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = {
            PostBookmarkPagingSource(
                bookmarkRepository = bookmarkRepository,
                user = User()
            )
        }
    ).flow.cachedIn(viewModelScope)

    fun refreshSavedPost(contents: LazyPagingItems<Content>) = viewModelScope.launch {
        screenState = screenState.copy(isRefreshing = true)
        delay(500)
        contents.refresh()
        screenState = screenState.copy(isRefreshing = false)
    }

    fun retrySavedPost(contents: LazyPagingItems<Content>) = viewModelScope.launch {
        screenState = screenState.copy(isLoading = true)
        delay(500)
        contents.retry()
        screenState = screenState.copy(isLoading = false)
    }
}