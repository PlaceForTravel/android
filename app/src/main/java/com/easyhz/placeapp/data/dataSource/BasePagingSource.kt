package com.easyhz.placeapp.data.dataSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * https://developer.android.com/reference/kotlin/androidx/paging/PagingSource
 *
 */
abstract class BasePagingSource<T : Any>: PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? = state.anchorPosition?.let {
        val closestPage = state.closestPageToPosition(it)
        closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
    }

    abstract suspend fun fetchData(page: Int): List<T>?

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> = try {
        val position = params.key ?: START_PAGE_INDEX
        val data = fetchData(position)
        if (data != null) {
            val isEnd = data.isEmpty()
            val prev = if (position == START_PAGE_INDEX) null else position - 1
            val next = if (isEnd) null else position + 1
            LoadResult.Page(data, prev, next)
        } else {
            LoadResult.Error(NullPointerException())
        }
    } catch (exception: Exception) {
        Log.d(javaClass.simpleName, "LoadResult Error:: $exception")
        LoadResult.Error(exception)
    }
    companion object {
        const val START_PAGE_INDEX = 1
    }
}