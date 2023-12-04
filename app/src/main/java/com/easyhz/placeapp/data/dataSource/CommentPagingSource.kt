package com.easyhz.placeapp.data.dataSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.easyhz.placeapp.domain.model.feed.comment.CommentContent
import com.easyhz.placeapp.domain.repository.feed.FeedRepository

class CommentPagingSource(
    private val feedRepository: FeedRepository,
    private val id: Int,
): PagingSource<Int, CommentContent>() {
    override fun getRefreshKey(state: PagingState<Int, CommentContent>): Int? = state.anchorPosition?.let {
        val closestPage = state.closestPageToPosition(it)
        closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommentContent> = try {
        val position = params.key ?: START_PAGE_INDEX
        val data = feedRepository.fetchComments(id = id, page = position)
        if (data.isSuccessful) {
            val content = data.body()?.content ?: listOf()
            val isEnd = content.isEmpty()
            val prev = if(position == START_PAGE_INDEX) null else position -1
            val next = if(isEnd) null else position + 1
            LoadResult.Page(content, prev, next)
        } else {
            LoadResult.Invalid()
        }
    } catch (exception: Exception) {
        Log.d("CommentPagingSource","LoadResult Error $exception")
        LoadResult.Error(exception)
    }

    companion object {
        const val START_PAGE_INDEX = 1
        const val PAGE_SIZE = 10
    }

}