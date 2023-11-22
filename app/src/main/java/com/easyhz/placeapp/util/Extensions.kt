package com.easyhz.placeapp.util

import android.database.Cursor
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import java.math.BigDecimal
import java.math.RoundingMode

fun Cursor.getLongColumnOrThrow(columnName: String): Long =
    getLong(getColumnIndexOrThrow(columnName))

fun Cursor.getStringColumnOrThrow(columnName: String): String? =
    getString(getColumnIndexOrThrow(columnName))


fun String.toBasicCategory() = this.split(">").last()

fun String.withoutHTML() = HtmlCompat.fromHtml(this, FROM_HTML_MODE_LEGACY).toString()

fun Double.toLatLng(newScale: Int) = BigDecimal(this).setScale(newScale, RoundingMode.HALF_UP).toDouble()

fun String.toAddress() = this.split(" ").slice(0..1).joinToString(" ")