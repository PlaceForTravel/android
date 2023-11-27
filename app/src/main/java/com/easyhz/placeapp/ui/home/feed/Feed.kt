package com.easyhz.placeapp.ui.home.feed

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.placeapp.constants.PaddingConstants.CONTENT_ALL
import com.easyhz.placeapp.ui.component.ContentCard
import com.easyhz.placeapp.ui.detail.getStatusBarColors
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape

data class FeedType(
    val id: Int,
    val imagePath: List<String>,
    val userName: String,
    val regDate: String,
    val placeName: String,
    val bookmarkCount: Int,
    val content: String? = null,
    val detailPlace: String? = null
)
@Composable
fun Feed(
    onItemClick: (Int) -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val dummy = listOf(
        FeedType(
            id = 1,
            imagePath = listOf("https://picsum.photos/id/177/200/300", "https://picsum.photos/id/337/200/300"),
            userName = "북치는 라이언",
            regDate = "2023.10.29",
            placeName = "경기도 성남시 분당구",
            bookmarkCount = 5,
        ),FeedType(
            id = 2,
            imagePath = listOf("https://picsum.photos/id/352/200/300"),
            userName =  "유저 2",
            regDate = "2023.11.01",
            placeName = "대한민국 서울특별시",
            bookmarkCount = 0,
        ),FeedType(
            id = 3,
            imagePath = listOf("https://picsum.photos/id/234/200/300"),
            userName = "유저 3",
            regDate = "2023.11.01",
            placeName = "대한민국 여수시",
            bookmarkCount = 1342
        ),FeedType(
            id = 4,
            imagePath = listOf("https://picsum.photos/id/236/200/300"),
            userName= "유저 4",
            regDate = "2023.11.01",
            placeName = "대한민국 부산광역시",
            bookmarkCount = 434
        ),FeedType(
            id = 5,
            imagePath = listOf("https://picsum.photos/id/231/200/300"),
            userName =  "유저 5",
            regDate = "2023.11.01",
            placeName = "대한민국 대전광역시",
            bookmarkCount = 12
        )
    )
    LazyColumn(
        modifier = Modifier
            .background(PlaceAppTheme.colorScheme.subBackground)
    ) {
        itemsIndexed(dummy) {index, item ->
            ContentCard(
                item = item,
                imageSize = (screenWidth - 100).dp,
                cardWidth = screenWidth.dp,
                modifier = Modifier
                    .width(screenWidth.dp)
                    .padding(CONTENT_ALL.dp)
                    .clip(roundShape)
                    .background(PlaceAppTheme.colorScheme.mainBackground)
                    .clickable { onItemClick(item.id) },
            )
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