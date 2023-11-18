package com.easyhz.placeapp.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat


/**
 * 권한
 * : https://developer.android.com/training/permissions/requesting?hl=ko
 * : https://hanyeop.tistory.com/452
 * */
fun checkGalleryPermission(
    context: Context,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    action: () -> Unit,
) {
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES) -> {
            action()
        }
        else -> {
            launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}