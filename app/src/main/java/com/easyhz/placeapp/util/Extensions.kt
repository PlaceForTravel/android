package com.easyhz.placeapp.util

import android.database.Cursor

fun Cursor.getLongColumnOrThrow(columnName: String): Long =
    getLong(getColumnIndexOrThrow(columnName))

fun Cursor.getStringColumnOrThrow(columnName: String): String? =
    getString(getColumnIndexOrThrow(columnName))