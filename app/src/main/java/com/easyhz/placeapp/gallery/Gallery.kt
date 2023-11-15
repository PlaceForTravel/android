package com.easyhz.placeapp.gallery

import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.easyhz.placeapp.util.getLongColumnOrThrow
import com.easyhz.placeapp.util.getStringColumnOrThrow

data class Gallery(
    val id: Long,
    val path: String,
    val uri: Uri,
    val name: String,
    val regDate: String,
    val size: Int,
    var isSelected: Boolean = false
) {
    companion object {
        fun createFromCursor(cursor: Cursor, uri: Uri): Gallery {
            val id = cursor.getLongColumnOrThrow(MediaStore.Images.ImageColumns._ID)
            val name = cursor.getStringColumnOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME)
            val path = cursor.getStringColumnOrThrow(MediaStore.Images.ImageColumns.DATA)
            val regDate = cursor.getStringColumnOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN)
            val contentUri = ContentUris.withAppendedId(uri, id)

            return Gallery(
                id = id,
                path = path ?: "",
                uri = contentUri,
                name = name ?: "",
                regDate = regDate ?: "" ,
                size = 0
            )
        }
    }
}

