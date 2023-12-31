package com.easyhz.placeapp.ui.post

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easyhz.placeapp.R
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import com.easyhz.placeapp.ui.component.CircularLoading
import com.easyhz.placeapp.ui.component.detail.WindowShade
import com.easyhz.placeapp.ui.component.post.ImagesContent
import com.easyhz.placeapp.ui.component.post.MapSearchModal
import com.easyhz.placeapp.ui.component.post.PostHeader
import com.easyhz.placeapp.ui.component.post.TextContent
import com.easyhz.placeapp.ui.detail.getStatusBarColors
import com.easyhz.placeapp.ui.state.ApplicationState
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape
import com.easyhz.placeapp.util.borderBottom
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewPost(
    viewModel: NewPostViewModel = hiltViewModel(),
    searchModalViewModel: SearchModalViewModel = hiltViewModel(),
    applicationState: ApplicationState,
    onNavigateToBack: () -> Unit,
    onNavigateToNext: () -> Unit,
    feedDetail: FeedDetail? = null
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val window = (LocalContext.current as Activity).window
    val statusTopBar = PlaceAppTheme.colorScheme.statusBottomBar
    val statusBottomBar = PlaceAppTheme.colorScheme.statusBottomBar
    val placeBorderError = PlaceAppTheme.colorScheme.error
    val placeBorderDefault = PlaceAppTheme.colorScheme.secondaryBorder
    val isLightMode = !isSystemInDarkTheme()
    val modalHeight = 760.dp
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { viewModel.postState.post.places.size }
    val postState = viewModel.postState
    val isShowModal = searchModalViewModel.isShowModal.value
    val searchValue = searchModalViewModel.searchValue.value
    var title = R.string.post_new_post_header
    var isModify = feedDetail != null
    LaunchedEffect(key1 = Unit) {
        if (feedDetail != null ) {
            title = R.string.post_modify_post_header
            viewModel.setPostState(feedDetail, placeBorderDefault)
        }
    }

    LaunchedEffect(key1 = postState) {
        if (postState.onSuccess) {
            onNavigateToNext()
        } else if (postState.error != null) {
            applicationState.showSnackBar(context.getString(R.string.retry_error_message))
            viewModel.initError()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                searchModalViewModel.setIsShowModal(false)
                searchModalViewModel.setSearchValue("")
                focusManager.clearFocus()
            }
    ) {
        Column {
            PostHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .borderBottom(color = PlaceAppTheme.colorScheme.primaryBorder, width = 1.dp),
                title = stringResource(id = title),
                next = stringResource(id = R.string.post_complete_header),
                onBackClick = { onNavigateToBack() },
                onNextClick = {
                    when {
                        isModify -> feedDetail?.boardId?.let { viewModel.modifyPost(it) }
                        else -> viewModel.onNextClick(placeBorderError = placeBorderError) { index ->
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    }
                }
            )
            LazyColumn {
                item {
                    ImagesContent(
                        contents = postState.post.places,
                        pagerState = pagerState,
                        imageSize = screenWidth.dp,
                        modifier = Modifier.padding(10.dp),
                        onPlaceClick = {
                            if (isModify) {
                                applicationState.showSnackBar(context.getString(R.string.post_modify_post_content))
                            } else {
                                searchModalViewModel.setIsShowModal(true)
                            }
                        }
                    )
                }
                item {
                    TextContent(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .height(200.dp)
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = PlaceAppTheme.colorScheme.secondaryBorder,
                                shape = roundShape
                            ),
                        value = postState.post.content,
                        onValueChange = { viewModel.setTextContent(it)},
                        enabled = !isShowModal
                    )
                }
            }
        }

        if (postState.isLoading) {
            WindowShade()
            CircularLoading(scope = this)
        }
    }

    if (isShowModal) {
        WindowShade()
        MapSearchModal(
            query = searchValue,
            onQueryChange = {
                searchModalViewModel.setSearchValue(it)
                if (searchValue.isNotEmpty()) searchModalViewModel.setSearchActive(true)
            },
            onSearch = {
                searchModalViewModel.setSearchActive(false)
                viewModel.getPlaces(searchValue, 5, 1, "random")
            },
            onActiveChange = { searchModalViewModel.setSearchActive(it) },
            placeList = viewModel.placeList.value?.placeItems,
            isEqualCity = postState.isEqualCity,
            cityName = postState.post.cityName,
            onItemClick = { item ->
                if (viewModel.hasEqualCity(item, pagerState.currentPage)) {
                    viewModel.setPlaces(pagerState.currentPage, item, placeBorderDefault)
                    searchModalViewModel.setIsShowModal(false)
                    viewModel.setCityName(item)
                    viewModel.setIsEqualCity(true)
                } else {
                    viewModel.setIsEqualCity(false)
                }
                searchModalViewModel.setSearchValue("")
            },
            modifier = Modifier.height(modalHeight)
        )
    }

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

@Preview
@Composable
private fun NewPostPreview() {
    val scope = rememberCoroutineScope()
    PlaceAppTheme {
        NewPost(
            onNavigateToBack = { },
            onNavigateToNext = { },
            applicationState = ApplicationState(
                SnackbarHostState(), scope
            )
        )
    }
}
