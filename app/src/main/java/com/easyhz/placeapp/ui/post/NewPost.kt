package com.easyhz.placeapp.ui.post

import android.app.Activity
import android.util.Log
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easyhz.placeapp.R
import com.easyhz.placeapp.ui.component.detail.WindowShade
import com.easyhz.placeapp.ui.component.post.ImagesContent
import com.easyhz.placeapp.ui.component.post.MapSearchModal
import com.easyhz.placeapp.ui.component.post.PostHeader
import com.easyhz.placeapp.ui.component.post.TextContent
import com.easyhz.placeapp.ui.detail.getStatusBarColors
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape
import com.easyhz.placeapp.util.borderBottom
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewPost(
    viewModel: NewPostViewModel = hiltViewModel(),
    searchModalViewModel: SearchModalViewModel = hiltViewModel(),
    onNavigateToBack: () -> Unit,
    onNavigateToNext: () -> Unit,
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

    val scope = rememberCoroutineScope()
    var isUnselected by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState { viewModel.selectedImageList.size }
    val isShowModal = searchModalViewModel.isShowModal.value
    val searchValue = searchModalViewModel.searchValue.value
    val searchActive = searchModalViewModel.searchActive.value

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
                title = stringResource(id = R.string.post_new_post_header),
                next = stringResource(id = R.string.post_complete_header),
                onBackClick = { onNavigateToBack() },
                onNextClick = {
                    viewModel.onNextClick {
                        onNavigateToNext()
                    }
                    if (viewModel.unselectedImagePlaceIndices.isNotEmpty()) {
                        viewModel.setPlaceBorder(placeBorderDefault, placeBorderError)
                        scope.launch {
                            isUnselected = true
                            pagerState.animateScrollToPage(viewModel.unselectedImagePlaceIndices.first())
                            isUnselected = false
                        }
                    }
                }
            )
            LazyColumn {
                item {
                    ImagesContent(
                        contents = viewModel.selectedImagePlaceList,
                        pagerState = pagerState,
                        isUnselected = isUnselected,
                        imageSize = screenWidth.dp,
                        modifier = Modifier.padding(10.dp),
                        onPlaceClick = { searchModalViewModel.setIsShowModal(true) }
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
                        value = viewModel.textContent.value,
                        onValueChange = { viewModel.setTextContent(it)},
                        enabled = !isShowModal
                    )
                }
            }

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
            isEqualCity = viewModel.isEqualCity.value,
            tempCityName = viewModel.tempCityName.value,
            onItemClick = { item ->
                if (viewModel.hasEqualCity(item, pagerState.currentPage)) {
                    viewModel.setPlaceList(pagerState.currentPage, item, placeBorderDefault)
                    searchModalViewModel.setIsShowModal(false)
                    viewModel.setTempCityName(item)
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
    PlaceAppTheme {
        NewPost(
            onNavigateToBack = { },
            onNavigateToNext = { }
        )
    }
}
