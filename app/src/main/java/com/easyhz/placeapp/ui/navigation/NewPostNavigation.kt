package com.easyhz.placeapp.ui.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easyhz.placeapp.ui.post.GalleryScreen
import com.easyhz.placeapp.ui.post.NewPost

fun NavGraphBuilder.addNewPostGraph(
    onNavigateToBack: () -> Unit,
    onNavigateToNext: () -> Unit,
    onNavBackStack: () -> NavBackStackEntry,
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
        val backEntry = remember(backStackEntry) { onNavBackStack() }
        NewPost(
            viewModel = hiltViewModel(backEntry),
            onNavigateToBack = onNavigateToBack,
            onNavigateToNext = onNavigateToNext,
        )
    }
}