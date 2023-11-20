package com.easyhz.placeapp

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.easyhz.placeapp.ui.navigation.PostRoutes.GALLERY
import com.easyhz.placeapp.ui.navigation.addHomeGraph
import com.easyhz.placeapp.ui.navigation.addNewPostGraph
import com.easyhz.placeapp.ui.navigation.rememberMainNavController
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.util.checkGalleryPermission

@Composable
fun PlaceApp() {
    PlaceAppTheme {
        val mainNavController = rememberMainNavController()
        mainNavController.navController.currentBackStackEntryAsState().value?.destination
        val isHome = mainNavController.currentRoute?.startsWith(MainDestinations.HOME_ROUTE) == true
        val isFeed = mainNavController.currentRoute?.equals(HomeSections.FEED.route) == true
        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                mainNavController.navigateToNewPost()
                Log.d("PlaceApp", "권한 수락")
            } else {
                Log.d("PlaceApp", "권한 필요")
            }
        }
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
                        onClick = {
                            checkGalleryPermission(
                                context = context,
                                launcher = launcher,
                                action = mainNavController::navigateToNewPost
                        ) }
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
                    onNavigateToBoardDetail = mainNavController::navigateToBoardDetail,
                    onNavigateToBack = mainNavController::navigateToBack,
                    onNavigateToNext = mainNavController::navigateToNext,
                    onNavBackStack = mainNavController::getNewPostNavBackStack
                )
            }
        }
    }
}

/**
 * Navigation 관리
 */
private fun NavGraphBuilder.navGraph(
    onNavigateToBoardDetail: (Int, NavBackStackEntry) -> Unit,
    onNavigateToBack: () -> Unit,
    onNavigateToNext: () -> Unit,
    onNavBackStack: () -> NavBackStackEntry,
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
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val boardId = arguments.getInt(MainDestinations.BOARD_ID)

        BoardDetail(id = boardId)
    }
    navigation(
        route = MainDestinations.NEW_POST_ROUTE,
        startDestination = GALLERY
    ) {
        addNewPostGraph(
            onNavigateToBack = onNavigateToBack,
            onNavigateToNext = onNavigateToNext,
            onNavBackStack = onNavBackStack
        )
    }
}