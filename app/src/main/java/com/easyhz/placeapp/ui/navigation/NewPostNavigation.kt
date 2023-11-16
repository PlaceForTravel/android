package com.easyhz.placeapp.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easyhz.placeapp.ui.post.GalleryScreen
import com.easyhz.placeapp.ui.post.NewPost

fun NavGraphBuilder.addNewPostGraph(
    onNavigateToBack: () -> Unit,
    onNavigateToNext: () -> Unit,
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
    ) {
        NewPost(
            onNavigateToBack = onNavigateToBack,
            onNavigateToNext = onNavigateToNext,
        )
    }
}