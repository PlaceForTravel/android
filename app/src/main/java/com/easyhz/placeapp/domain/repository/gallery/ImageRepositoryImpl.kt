package com.easyhz.placeapp.domain.repository.gallery

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.os.bundleOf
import com.easyhz.placeapp.domain.model.gallery.Gallery
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * 1. Content Provider : https://developer.android.com/guide/topics/providers/content-provider-basics?hl=ko
 * 2. Data Storage: https://developer.android.com/training/data-storage/shared/media?hl=ko
 */
class ImageRepositoryImpl
@Inject constructor(
    @ApplicationContext private val context: Context,
) : ImageRepository {

    private val uri: Uri by lazy {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
    }

    private val contentResolver by lazy {
        context.contentResolver
    }

    /*
    * val projection = arrayOf(media-database-columns-to-retrieve) : Select
    * val selection = sql-where-clause-with-placeholder-variables : where
    * val selectionArgs = values-of-placeholder-variables :
    * val sortOrder = sql-order-by-clause
    * */
    private val projection = arrayOf(
        MediaStore.Images.ImageColumns._ID,
        MediaStore.Images.ImageColumns.DISPLAY_NAME,
        MediaStore.Images.ImageColumns.DATA,
        MediaStore.Images.ImageColumns.DATE_TAKEN,
        MediaStore.Images.ImageColumns.SIZE,
    )

    private val orderByThis = MediaStore.Images.ImageColumns.DATE_TAKEN

    override fun fetchImages(
        page: Int,
        loadSize: Int,
        currentLocation: String?,
    ): MutableList<Gallery> {
        val imageList = mutableListOf<Gallery>()
        var selection: String? = null
        var selectionArgs: Array<String>? = null
        if (currentLocation != null) {
            selection = "${MediaStore.Images.Media.DATA} LIKE ? "
            selectionArgs = arrayOf("%$currentLocation%")
        }

        val offset = (page - 1) * loadSize
        val query = getQuery(offset, loadSize, selection, selectionArgs)
        query?.use { cursor ->
            while (cursor.moveToNext()) {
                val image = Gallery.createFromCursor(cursor, uri)
                imageList.add(image)
            }
        }
        return imageList
    }

    private fun getQuery(
        offset: Int,
        limit: Int,
        selection : String?,
        selectionArgs: Array<String>?
    ) = if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
        val bundle = bundleOf(
            ContentResolver.QUERY_ARG_OFFSET to offset,
            ContentResolver.QUERY_ARG_LIMIT to limit,
            ContentResolver.QUERY_ARG_SORT_COLUMNS to arrayOf(MediaStore.Files.FileColumns.DATE_TAKEN),
            ContentResolver.QUERY_ARG_SORT_DIRECTION to ContentResolver.QUERY_SORT_DIRECTION_DESCENDING,
            ContentResolver.QUERY_ARG_SQL_SELECTION to selection,
            ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS to selectionArgs,
        )
        contentResolver.query(uri, projection, bundle, null)
    } else {
        contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs,
            "$orderByThis DESC LIMIT $limit OFFSET $offset"
        )
    }
}
