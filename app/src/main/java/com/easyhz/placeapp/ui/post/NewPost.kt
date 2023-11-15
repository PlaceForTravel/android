package com.easyhz.placeapp.ui.post

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

//const val GRID_CELL = 4
@Composable
fun NewPost(
    viewModel: NewPostViewModel = hiltViewModel()
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Text(text = "NewPost")
    }
}

@Composable
private fun Selected() {

}
@Preview
@Composable
fun NewPostPreview() {

}
