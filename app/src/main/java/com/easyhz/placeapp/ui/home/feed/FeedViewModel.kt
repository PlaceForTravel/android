package com.easyhz.placeapp.ui.home.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easyhz.placeapp.data.dataSource.FeedPagingSource
import com.easyhz.placeapp.data.dataSource.FeedPagingSource.Companion.PAGE_SIZE
import com.easyhz.placeapp.domain.model.feed.Content
import com.easyhz.placeapp.domain.repository.feed.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel
@Inject constructor(
    private val feedRepository: FeedRepository
): ViewModel() {

    private val _feedContentList = MutableStateFlow<PagingData<Content>>(PagingData.empty())
    val feedContentList: StateFlow<PagingData<Content>>
        get() = _feedContentList.asStateFlow()

    /**
     * 게시물 가져 오기
     **/
    fun fetchFeed() = viewModelScope.launch {
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                FeedPagingSource(
                    feedRepository = feedRepository
                )
            }
        ).flow.cachedIn(viewModelScope).collectLatest {
            _feedContentList.value = it
        }
    }
}