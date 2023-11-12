package com.easyhz.placeapp.ui.post

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.request.ImageRequest
import com.easyhz.placeapp.ui.component.ImageLoader

@Composable
fun NewPost() {
    var selectedImageUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris -> selectedImageUris = uris }
    )
    Button(onClick = {
        imagePickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }) {

    }

    LazyColumn{
        itemsIndexed(selectedImageUris) { index, item ->
            val imageRequest = ImageRequest.Builder(LocalContext.current)
                .data(item)
                .crossfade(true)
                .build()
            ImageLoader(image = imageRequest, contentDescription = "")
        }
    }
}

@Preview
@Composable
fun NewPostPreview() {

}
