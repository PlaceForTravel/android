package com.easyhz.placeapp.ui.home.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easyhz.placeapp.constants.ContentCardIcons
import com.easyhz.placeapp.constants.PaddingConstants
import com.easyhz.placeapp.ui.component.ContentCard
import com.easyhz.placeapp.ui.home.feed.FeedType
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape

const val GRID_CELL = 2
@Composable
fun Profile() {
    val screenWidth = LocalConfiguration.current.screenWidthDp / GRID_CELL
    val dummy = listOf(
        FeedType(
            id = 1,
            imagePath = listOf("https://picsum.photos/id/177/200/300", "https://picsum.photos/id/337/200/300"),
            userName = "북치는 라이언",
            regDate = "2023.10.29",
            placeName = "경기도 성남시 분당구",
            bookmarkCount = 5,
        ), FeedType(
            id = 2,
            imagePath = listOf("https://picsum.photos/id/352/200/300"),
            userName =  "유저 2",
            regDate = "2023.11.01",
            placeName = "대한민국 서울특별시",
            bookmarkCount = 0,
        ), FeedType(
            id = 3,
            imagePath = listOf("https://picsum.photos/id/234/200/300"),
            userName = "유저 3",
            regDate = "2023.11.01",
            placeName = "대한민국 여수시",
            bookmarkCount = 1342
        ), FeedType(
            id = 4,
            imagePath = listOf("https://picsum.photos/id/236/200/300"),
            userName= "유저 4",
            regDate = "2023.11.01",
            placeName = "대한민국 부산광역시",
            bookmarkCount = 434
        ), FeedType(
            id = 5,
            imagePath = listOf("https://picsum.photos/id/231/200/300"),
            userName =  "유저 5",
            regDate = "2023.11.01",
            placeName = "대한민국 대전광역시",
            bookmarkCount = 12
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PlaceAppTheme.colorScheme.subBackground)
    ) {
        ProfileView(
            modifier = Modifier.padding(10.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_CELL),
            contentPadding = PaddingValues(1.dp),
            modifier = Modifier
                .background(PlaceAppTheme.colorScheme.subBackground)
        ) {
            itemsIndexed(dummy) {index, item ->
                ContentCard(
                    item = item,
                    imageSize = (screenWidth - 50).dp,
                    cardWidth = screenWidth.dp,
                    isProfile = true,
                    modifier = Modifier
                        .width(screenWidth.dp)
                        .padding(PaddingConstants.PROFILE_CONTENT_ALL.dp)
                        .clip(roundShape)
                        .background(PlaceAppTheme.colorScheme.mainBackground)
                        .clickable { },
                )
            }
        }
    }
}

@Composable
private fun ProfileView(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ContentCardIcons.USER.icon,
                contentDescription = stringResource(id = ContentCardIcons.USER.label),
                tint = LocalContentColor.current,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = "유저",
                style = TextStyle.Default.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Justify,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false,
                    )
                ),
            )
        }
    }
}


@Preview
@Composable
private fun ProfilePreview() {
    Profile()
}