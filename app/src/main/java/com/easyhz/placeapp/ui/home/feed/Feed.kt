package com.easyhz.placeapp.ui.home.feed

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.easyhz.placeapp.constants.PaddingConstants.CONTENT_ALL
import com.easyhz.placeapp.ui.component.ContentCard
import com.easyhz.placeapp.ui.detail.getStatusBarColors
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape

@Composable
fun Feed(
    viewModel: FeedViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val contents = viewModel.feedContentList.collectAsLazyPagingItems()
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchFeed()
    }
    LazyColumn(
        modifier = Modifier
            .background(PlaceAppTheme.colorScheme.subBackground)
    ) {
        items(contents.itemCount) { index ->
            contents[index]?.let { item ->
                ContentCard(
                    item = item,
                    imageSize = (screenWidth - 100).dp,
                    cardWidth = screenWidth.dp,
                    modifier = Modifier
                        .width(screenWidth.dp)
                        .padding(CONTENT_ALL.dp)
                        .clip(roundShape)
                        .background(PlaceAppTheme.colorScheme.mainBackground)
                        .clickable { onItemClick(item.boardId) }
                )
            }
        }
    }

    val window = (LocalContext.current as Activity).window
    val statusTopBar = PlaceAppTheme.colorScheme.statusTopBar
    val statusBottomBar = PlaceAppTheme.colorScheme.statusBottomBar
    val isLightMode = !isSystemInDarkTheme()

    SideEffect {
        getStatusBarColors(
            isLightMode = isLightMode,
            window = window,
            statusTopBar = statusTopBar,
            statusBottomBar = statusBottomBar
        )
    }
}



@Preview("default")
@Preview("DarkMode", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun FeedPreview() {
    PlaceAppTheme {
        Feed(
            onItemClick = { }
        )
    }
}