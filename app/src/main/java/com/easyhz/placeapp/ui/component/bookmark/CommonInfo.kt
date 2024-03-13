package com.easyhz.placeapp.ui.component.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.easyhz.placeapp.R
import com.easyhz.placeapp.ui.component.NoContentError
import com.easyhz.placeapp.ui.home.bookmark.BookmarkTabs
import com.easyhz.placeapp.ui.theme.PlaceAppTheme

@Composable
fun NoContent(
    scope: BoxScope,
    type: BookmarkTabs,
) {
    val (mainText, subText) = when (type) {
        BookmarkTabs.POST -> {
            Pair(R.string.no_bookmark_post_content, R.string.no_bookmark_post_content_sub)
        }
        BookmarkTabs.PLACE -> {
            Pair(R.string.no_bookmark_place_content, R.string.no_bookmark_place_content_sub)
        }
    }
    scope.apply {
        NoContentError(
            scope = this,
            mainText = stringResource(id = mainText),
            subText = stringResource(id = subText),
        )
    }
}

@Preview
@Composable
private fun NoContentPreview() {
    PlaceAppTheme {
        Box {
            NoContent(this, type = BookmarkTabs.PLACE)
        }
    }
}