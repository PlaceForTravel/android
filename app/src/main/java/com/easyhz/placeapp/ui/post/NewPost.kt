package com.easyhz.placeapp.ui.post

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easyhz.placeapp.R
import com.easyhz.placeapp.ui.component.post.PostHeader
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.util.borderBottom

//const val GRID_CELL = 4
@Composable
fun NewPost(
    viewModel: NewPostViewModel = hiltViewModel(),
    onNavigateToBack: () -> Unit,
    onNavigateToNext: () -> Unit,
) {

    Column {
        PostHeader(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .borderBottom(color = PlaceAppTheme.colorScheme.primaryBorder, dp = 1.dp),
            title = stringResource(id = R.string.post_new_post_header),
            onBackClick = { onNavigateToBack() },
            onNextClick = { onNavigateToNext() }
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
        ) {
            Text(text = "NewPost")
        }
    }
}

@Composable
private fun Selected() {

}
@Preview
@Composable
fun NewPostPreview() {

}
