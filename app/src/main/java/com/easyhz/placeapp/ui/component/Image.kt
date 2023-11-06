package com.easyhz.placeapp.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.easyhz.placeapp.R

@Composable
fun ImageLoader(
    image: ImageRequest,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = image,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        filterQuality = FilterQuality.None,
        placeholder = painterResource(id = R.drawable.ic_launcher_background),
        modifier = modifier
    )
}