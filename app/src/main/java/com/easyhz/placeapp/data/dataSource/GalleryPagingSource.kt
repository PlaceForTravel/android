package com.easyhz.placeapp.data.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.easyhz.placeapp.domain.model.gallery.Gallery
import com.easyhz.placeapp.domain.repository.gallery.ImageRepository

/**
 * https://developer.android.com/reference/kotlin/androidx/paging/PagingSource
 *
 */
class GalleryPagingSource(
    private val imageRepository: ImageRepository,
    private val currentLocation: String?
): PagingSource<Int, Gallery>() {
    override fun getRefreshKey(state: PagingState<Int, Gallery>): Int? = state.anchorPosition?.let {
        val closestPage = state.closestPageToPosition(it)
        closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gallery> = try {
        val position = params.key ?: START_PAGE_INDEX
        val data = imageRepository.fetchImages(
            page = position,
            loadSize = params.loadSize,
            currentLocation = currentLocation,
        )

        val isEnd = data.isEmpty()
        val prev = if(position == START_PAGE_INDEX) null else position - 1
        val next = if(isEnd) null else position + (params.loadSize / PAGE_SIZE)
        LoadResult.Page(data, prev, next)
    } catch (exception: Exception) {
        LoadResult.Error(exception)
    }


    companion object {
        const val START_PAGE_INDEX = 1
        const val PAGE_SIZE = 30
    }
}