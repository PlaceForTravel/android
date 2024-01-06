package com.easyhz.placeapp.ui.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import com.easyhz.placeapp.ui.post.GalleryScreen
import com.easyhz.placeapp.ui.post.NewPost
import com.easyhz.placeapp.ui.state.ApplicationState

fun NavGraphBuilder.addNewPostGraph(
    applicationState: ApplicationState,
    onNavigateToBack: () -> Unit,
    onNavigateToNext: () -> Unit,
    getNavBackStack: () -> NavBackStackEntry,
    getFeedDetail: () -> FeedDetail?
) {
    composable(
        route = "${MainDestinations.NEW_POST_ROUTE}/${PostRoutes.GALLERY}"
    ) {
        GalleryScreen(
            onNavigateToBack = onNavigateToBack,
            onNavigateToNext = onNavigateToNext,
        )
    }
    composable(
        route = "${MainDestinations.NEW_POST_ROUTE}/${PostRoutes.NEW_POST}"
    ) { backStackEntry ->
        val backEntry = remember(backStackEntry) { getNavBackStack() }
        NewPost(
            viewModel = hiltViewModel(backEntry),
            applicationState = applicationState,
            onNavigateToBack = onNavigateToBack,
            onNavigateToNext = onNavigateToNext,
        )
    }
    composable(
        route = "${MainDestinations.NEW_POST_ROUTE}/${PostRoutes.MODIFY}"
    ) {
        val argument = getFeedDetail()
        NewPost(
            applicationState = applicationState,
            onNavigateToBack = onNavigateToBack,
            onNavigateToNext = onNavigateToNext,
            feedDetail = argument
        )
    }
}