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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.easyhz.placeapp.constants.PaddingConstants
import com.easyhz.placeapp.constants.PaddingConstants.CONTENT_ALL
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import com.easyhz.placeapp.domain.model.user.UserManager.needLogin
import com.easyhz.placeapp.ui.component.DetailContentCard
import com.easyhz.placeapp.ui.component.CircularLoading
import com.easyhz.placeapp.ui.component.SpaceDivider
import com.easyhz.placeapp.ui.component.comment.CommentCard
import com.easyhz.placeapp.ui.component.comment.CommentTextField
import com.easyhz.placeapp.ui.component.detail.DetailActions
import com.easyhz.placeapp.ui.component.detail.DetailBottomSheet
import com.easyhz.placeapp.ui.component.detail.MapModal
import com.easyhz.placeapp.ui.component.detail.WindowShade
import com.easyhz.placeapp.ui.state.ApplicationState
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundBottomShape
import com.easyhz.placeapp.ui.theme.roundShape
import com.easyhz.placeapp.ui.theme.roundTopShape
import com.easyhz.placeapp.util.login_require

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoardDetail(
    viewModel: BoardDetailViewModel = hiltViewModel(),
    modalViewModel: MapModalViewModel = hiltViewModel(),
    applicationState: ApplicationState,
    id: Int,
    onNavigateToHome: () -> Unit,
    onNavigateToModify: (FeedDetail) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val focusManager = LocalFocusManager.current

    val comments = viewModel.comments.collectAsLazyPagingItems()
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchFeedDetail(id)
        viewModel.fetchComments(id)
        viewModel.initDetailState()
    }

    LaunchedEffect(key1 = viewModel.commentState.isSuccessful) {
        viewModel.fetchComments(id)
        viewModel.resetCommentState()
        focusManager.clearFocus()
    }
    LaunchedEffect(key1 = viewModel.detailState) {
        when(viewModel.detailState.type) {
            DetailActions.DELETE -> {
                if(viewModel.detailState.isSuccessful) {
                    sheetState.hide()
                    onNavigateToHome()
                }
            }
            DetailActions.MODIFY -> {
                sheetState.hide()
                viewModel.feedDetail.value?.let { onNavigateToModify(it) }
            }
            else -> {

            }
        }
    }

    viewModel.feedDetail.value?.let { feedDetail ->
        Box(
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.clickable {
                    onModalClose(modalViewModel, focusManager)
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
                                item = feedDetail,
                                imageSize = (screenWidth - 100).dp,
                                cardWidth = screenWidth.dp,
                                modifier = Modifier
                                    .width(screenWidth.dp)
                                    .padding(CONTENT_ALL.dp)
                                    .clip(roundShape)
                                    .background(PlaceAppTheme.colorScheme.mainBackground),
                                onMapClick = { placeImagesItem ->
                                    viewModel.setPlaceImagesItem(placeImagesItem)
                                    modalViewModel.isShowModal = true
                                },
                                onSaveClick = {
                                    viewModel.savePost(it, applicationState)
                                },
                                onMoreClick = { viewModel.setIsSheetOpen(true) }
                            )
                        }
                        items(comments.itemCount) { index ->
                            val isTop = index == 0
                            val isBottom = index == comments.itemCount - 1

                            Box(
                                modifier = Modifier
                                    .width(screenWidth.dp)
                                    .padding(horizontal = CONTENT_ALL.dp)
                                    .clip(
                                        when {
                                            comments.itemCount == 1 -> roundShape
                                            isTop -> roundTopShape
                                            isBottom -> roundBottomShape
                                            else -> RectangleShape
                                        }
                                    )
                                    .background(PlaceAppTheme.colorScheme.mainBackground),
                            ) {
                                comments[index]?.let {
                                    CommentCard(
                                        item = it,
                                        modifier = Modifier.padding(
                                            horizontal = PaddingConstants.ICON_TEXT_HORIZONTAL.dp,
                                            vertical = PaddingConstants.ICON_TEXT_VERTICAL.dp
                                        )
                                    )
                                }
                            }
                        }
                        item {
                            SpaceDivider(padding = 5)
                        }
                    }
                    CommentTextField(
                        modifier = Modifier
                            .padding(CONTENT_ALL.dp)
                            .fillMaxHeight(0.9F)
                            .fillMaxWidth(),
                        value = viewModel.commentState.postComment.content,
                        onValueChange = { viewModel.setCommentText(it) },
                        enabled = !modalViewModel.isShowModal,
                        onSendClick = {
                            if (needLogin) {
                                applicationState.showSnackBar(login_require)
                            } else {
                                viewModel.saveComment(id)
                            }
                        }
                    )
                }
            }
            if (modalViewModel.isShowModal) {
                WindowShade()
                viewModel.placeImagesItem.value?.let { placeImageItem ->
                    MapModal(
                        item = placeImageItem,
                        places = viewModel.allPlaces,
                        modifier = Modifier
                            .height((screenHeight * 0.7).dp)
                            .width((screenWidth - 100).dp),
                        isViewAll = modalViewModel.isViewAll.value,
                        onViewAllClick = { modalViewModel.setIsViewAll(!modalViewModel.isViewAll.value) },
                        onClose = { onModalClose(modalViewModel, focusManager) },
                        onSaved = { modalViewModel.savePlace(it, applicationState) }
                    )
                }
            }

            if(viewModel.isSheetOpen) {
                DetailBottomSheet(
                    id = id,
                    sheetState = sheetState,
                    onDismissRequest = {
                        viewModel.setIsSheetOpen(false)
                    }
                )
            }
            if (viewModel.isLoading.value || viewModel.commentState.isLoading) {
                WindowShade()
                CircularLoading(this)
            }
        }
    } ?: Text(text = "오류") // TODO:: 없을 때 잡기

    val window = (LocalContext.current as Activity).window
    val statusTopBar = PlaceAppTheme.colorScheme.statusTopBar
    val statusBottomBar = PlaceAppTheme.colorScheme.statusBottomBar
    val isLightMode = !isSystemInDarkTheme()

    SideEffect {
        getStatusBarColors(
            isShowBottomSheet = modalViewModel.isShowModal,
            isLightMode = isLightMode,
            window = window,
            statusTopBar = statusTopBar,
            statusBottomBar = statusBottomBar
        )
    }
}

fun onModalClose(viewModel: MapModalViewModel, focusManager: FocusManager) {
    viewModel.isShowModal = false
    viewModel.setIsViewAll(false)
    focusManager.clearFocus()
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
    val scope = rememberCoroutineScope()
    val a = ApplicationState(SnackbarHostState(), scope)
    PlaceAppTheme {
        BoardDetail(
            id = 1,
            applicationState = a,
            onNavigateToHome = { }
        ) { }
    }
}
