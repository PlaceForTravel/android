package com.easyhz.placeapp.ui.detail

import android.app.Activity
import android.content.res.Configuration
import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easyhz.placeapp.constants.PaddingConstants.CONTENT_ALL
import com.easyhz.placeapp.ui.component.DetailContentCard
import com.easyhz.placeapp.ui.component.comment.CommentTextField
import com.easyhz.placeapp.ui.component.comment.Comments
import com.easyhz.placeapp.ui.component.detail.MapModal
import com.easyhz.placeapp.ui.component.detail.WindowShade
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape

@Composable
fun BoardDetail(
    viewModel: BoardDetailViewModel = hiltViewModel(),
    id: Int
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val mock = arrayOf(
        CommentType(
            id = 1,
            userName = "유저1",
            content = "댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.",
            regDate = "2023.11.08"
        ),CommentType(
            id = 2,
            userName = "유저2",
            content = "댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.",
            regDate = "2023.11.08"
        ),CommentType(
            id = 3,
            userName = "유저3",
            content = "댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.",
            regDate = "2023.11.08"
        ),CommentType(
            id = 4,
            userName = "유저4",
            content = "댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.",
            regDate = "2023.11.08"
        ),CommentType(
            id = 5,
            userName = "유저5",
            content = "댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.",
            regDate = "2023.11.08"
        ),CommentType(
            id = 6,
            userName = "유저6",
            content = "댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.댓글 입니다.",
            regDate = "2023.11.08"
        ),
    )
    var isShowModal by remember { mutableStateOf(false) }
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val focusManager = LocalFocusManager.current

    val window = (LocalContext.current as Activity).window
    val statusTopBar = PlaceAppTheme.colorScheme.statusTopBar
    val statusBottomBar = PlaceAppTheme.colorScheme.statusBottomBar

    LaunchedEffect(key1 = Unit) {
        println(id)
        viewModel.fetchFeedDetail(id)
    }

    viewModel.feedDetail.value?.let {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.clickable {
                    isShowModal = false
                    viewModel.setIsViewAll()
                    focusManager.clearFocus()
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .background(PlaceAppTheme.colorScheme.subBackground)
                ) {
                    LazyColumn(
                        Modifier.weight(1f)
                    ) {
                        item {
                            DetailContentCard(
                                item = it,
                                imageSize = (screenWidth - 100).dp,
                                cardWidth = screenWidth.dp,
                                modifier = Modifier
                                    .width(screenWidth.dp)
                                    .padding(CONTENT_ALL.dp)
                                    .clip(roundShape)
                                    .background(PlaceAppTheme.colorScheme.mainBackground),
                                onMapClick = { placeImagesItem ->
                                    viewModel.setPlaceImagesItem(placeImagesItem)
                                    isShowModal = true
                                }
                            )
                        }
                        item {
                            Comments(
                                items = mock,
                                modifier = Modifier
                                    .width(screenWidth.dp)
                                    .padding(CONTENT_ALL.dp)
                                    .clip(roundShape)
                                    .background(PlaceAppTheme.colorScheme.mainBackground),
                            )
                        }
                    }
                    CommentTextField(
                        modifier = Modifier.padding(CONTENT_ALL.dp).fillMaxHeight(0.9F).fillMaxWidth(),
                        value = viewModel.commentText.value,
                        onValueChange = { viewModel.setCommentText(it) },
                        enabled = !isShowModal,
                        onSendClick = { println("보냈음") }
                    )
                }

            }
            if (isShowModal) {
                WindowShade()
                viewModel.placeImagesItem.value?.let { placeImageItem ->
                    MapModal(
                        item = placeImageItem,
                        places = viewModel.allPlaces,
                        modifier = Modifier
                            .height((screenHeight * 0.7).dp)
                            .width((screenWidth - 100).dp),
                        isViewAll = viewModel.isViewAll.value,
                        onViewAllClick = { viewModel.setIsViewAll() }
                    )
                }
            }
        }
    } ?: Text(text = "오류") // TODO:: 없을 때 잡기
    val isLightMode = !isSystemInDarkTheme()
    SideEffect {
        getStatusBarColors(
            isShowBottomSheet = isShowModal,
            isLightMode = isLightMode,
            window = window,
            statusTopBar = statusTopBar,
            statusBottomBar = statusBottomBar
        )
    }
}

data class CommentType (
    val id: Int,
    val userName: String,
    val content: String,
    val regDate: String
)

private fun onMapClick() {

}
fun getStatusBarColors(
    isShowBottomSheet: Boolean = false,
    isLightMode: Boolean,
    window: Window,
    statusTopBar: Color,
    statusBottomBar: Color
) {
    if(isShowBottomSheet && isLightMode) {
        window.statusBarColor = Color.Black.copy(alpha = 0.5f).toArgb()
        window.navigationBarColor = Color.Black.copy(alpha = 0.5f).toArgb()
    } else {
        window.statusBarColor = statusTopBar.toArgb()
        window.navigationBarColor = statusBottomBar.toArgb()
    }
}

@Preview("default")
@Preview("DarkMode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FeedPreview() {
    PlaceAppTheme {
        BoardDetail(
            id = 1
        )
    }
}