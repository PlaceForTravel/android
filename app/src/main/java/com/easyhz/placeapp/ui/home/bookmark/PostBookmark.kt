package com.easyhz.placeapp.ui.home.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.easyhz.placeapp.ui.component.CircularLoading
import com.easyhz.placeapp.ui.component.NetworkError
import com.easyhz.placeapp.ui.component.detail.WindowShade
import com.easyhz.placeapp.ui.home.feed.FeedContent
import com.easyhz.placeapp.ui.home.feed.FeedViewModel
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicatorDefaults
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostBookmark(
    viewModel: BookmarkViewModel = hiltViewModel(),
    feedViewModel: FeedViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val contents = viewModel.pager.collectAsLazyPagingItems()
    val refreshState = rememberPullRefreshState(
        refreshing = viewModel.screenState.isRefreshing,
        onRefresh = { viewModel.refreshSavedPost(contents) }
    )
    Box {
        Column {
            Box(modifier = Modifier.fillMaxSize()) {
                when(contents.loadState.refresh) {
                    is LoadState.Error -> {
                        NetworkError(scope = this) {
                            viewModel.retrySavedPost(contents)
                        }
                    }
                    else -> {
                        FeedContent(
                            contents = contents,
                            refreshState = refreshState,
                            screenWidth = screenWidth,
                            onItemClick = onItemClick,
                        ) { id -> feedViewModel.savePost(id, contents) }
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
}