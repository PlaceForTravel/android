package com.easyhz.placeapp.ui.home.feed

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.easyhz.placeapp.constants.PaddingConstants.CONTENT_ALL
import com.easyhz.placeapp.domain.model.feed.Content
import com.easyhz.placeapp.domain.model.user.UserManager
import com.easyhz.placeapp.ui.component.CircularLoading
import com.easyhz.placeapp.ui.component.ContentCard
import com.easyhz.placeapp.ui.component.NetworkError
import com.easyhz.placeapp.ui.component.detail.WindowShade
import com.easyhz.placeapp.ui.component.dialog.LoginDialog
import com.easyhz.placeapp.ui.component.search.SubSearchBar
import com.easyhz.placeapp.ui.detail.getStatusBarColors
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicatorDefaults
import eu.bambooapps.material3.pullrefresh.PullRefreshState
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Feed(
    viewModel: FeedViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit,
    onSearchBarClick: () -> Unit,
    onNavigateToUser: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val contents = viewModel.pager.collectAsLazyPagingItems()
    
    val refreshState = rememberPullRefreshState(refreshing = viewModel.screenState.isRefreshing, onRefresh = {
        viewModel.refreshFeed(contents)
    })
    val dialogCondition = viewModel.isFirstRun && viewModel.isShowDialog && (UserManager.user == null || UserManager.user?.fcmToken == null)

    Box {
        Column {
            SubSearchBar(
                onClick = onSearchBarClick
            )
            Box(modifier = Modifier.fillMaxSize()) {
                when(contents.loadState.refresh) {
                    is LoadState.Error -> {
                        NetworkError(scope = this) {
                            viewModel.retryFeed(contents)
                        }
                    }
                    else -> {
                        FeedContent(
                            contents = contents,
                            refreshState = refreshState,
                            screenWidth = screenWidth,
                            onItemClick = onItemClick,
                        ) { id -> viewModel.savePost(id, contents) }
                    }
                }
                PullRefreshIndicator(
                    refreshing = viewModel.screenState.isRefreshing,
                    state = refreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    colors = PullRefreshIndicatorDefaults.colors(contentColor = PlaceAppTheme.colorScheme.primary),
                )
            }
        }
        if (viewModel.screenState.isLoading) {
            WindowShade()
            CircularLoading(scope = this)
        }
    }
    if (dialogCondition) {
        LoginDialog(
            onDismissRequest = { viewModel.setIsShowDialog(false) },
            onLoginClick = {
                onNavigateToUser()
                viewModel.setIsShowDialog(false)
            },
            onDoNotShowAgainClick = { viewModel.setIsShowDialog(false) }
        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedContent(
    contents:  LazyPagingItems<Content>,
    refreshState: PullRefreshState,
    screenWidth: Int,
    onItemClick: (Int) -> Unit,
    onSaveClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .background(PlaceAppTheme.colorScheme.subBackground)
            .pullRefresh(refreshState)
    ) {
        items(contents.itemCount, key = { item -> item }) { index ->
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
                        .clickable { onItemClick(item.boardId) },
                    onSaveClick = { onSaveClick(it) }
                )
            }
        }
    }
}


@Preview("default")
@Preview("DarkMode", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun FeedPreview() {
    PlaceAppTheme {
        Feed(
            onItemClick = { },
            onSearchBarClick = { }
        ) {

        }
    }
}