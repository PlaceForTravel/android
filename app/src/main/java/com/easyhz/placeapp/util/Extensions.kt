package com.easyhz.placeapp.util

import android.database.Cursor
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY

fun Cursor.getLongColumnOrThrow(columnName: String): Long =
    getLong(getColumnIndexOrThrow(columnName))

fun Cursor.getStringColumnOrThrow(columnName: String): String? =
    getString(getColumnIndexOrThrow(columnName))


fun String.toBasicCategory() = this.split(">").last()

fun String.withoutHTML() = HtmlCompat.fromHtml(this, FROM_HTML_MODE_LEGACY).toString()