package com.easyhz.placeapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.easyhz.placeapp.ui.detail.BoardDetail
import com.easyhz.placeapp.ui.navigation.BottomBar
import com.easyhz.placeapp.ui.navigation.HomeSections
import com.easyhz.placeapp.ui.navigation.MainDestinations
import com.easyhz.placeapp.ui.navigation.MainFloatingActionButton
import com.easyhz.placeapp.ui.navigation.PostRoutes.NEW_POST
import com.easyhz.placeapp.ui.navigation.addHomeGraph
import com.easyhz.placeapp.ui.navigation.rememberMainNavController
import com.easyhz.placeapp.ui.post.GalleryScreen
import com.easyhz.placeapp.ui.theme.PlaceAppTheme

@Composable
fun PlaceApp() {
    PlaceAppTheme {
        val mainNavController = rememberMainNavController()
        mainNavController.navController.currentBackStackEntryAsState().value?.destination
        val isHome = mainNavController.currentRoute?.startsWith(MainDestinations.HOME_ROUTE) == true
        val isFeed = mainNavController.currentRoute?.equals(HomeSections.FEED.route) == true
        Scaffold(
            bottomBar = {
                if(isHome) {
                    BottomBar(
                        tabs = HomeSections.values(),
                        currentRoute = mainNavController.currentRoute ?: HomeSections.FEED.route,
                        onNavigateToRoute = mainNavController::navigateToBottomBarRoute
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                if(isFeed) {
                    MainFloatingActionButton(
                        onClick = mainNavController::navigateToNewPost
                    )
                }
            },

        ) { paddingValue ->
            NavHost(
                navController = mainNavController.navController,
                startDestination = MainDestinations.HOME_ROUTE,
                modifier = Modifier.padding(paddingValue)
            ) {
                navGraph(
                    onNavigateToBoardDetail = mainNavController::navigateToBoardDetail
                )
            }
        }
    }
}

/**
 * Navigation 관리
 */
private fun NavGraphBuilder.navGraph(
    onNavigateToBoardDetail: (Int, NavBackStackEntry) -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.FEED.route
    ) {
        addHomeGraph(
            onNavigateToBoardDetail = onNavigateToBoardDetail
        )
    }
    composable(
        route = "${MainDestinations.BOARD_DETAIL_ROUTE}/{${MainDestinations.BOARD_ID}}",
        arguments = listOf(navArgument(MainDestinations.BOARD_ID) { type = NavType.IntType })
    ) {backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val boardId = arguments.getInt(MainDestinations.BOARD_ID)

        BoardDetail(id = boardId)
    }

    composable(
        route = "${MainDestinations.POST_ROUTE}/$NEW_POST"
    ) {
//        NewPost()
        GalleryScreen()
    }
}