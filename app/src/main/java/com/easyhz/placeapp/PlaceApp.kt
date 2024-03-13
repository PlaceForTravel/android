package com.easyhz.placeapp

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import com.easyhz.placeapp.domain.model.user.UserManager.needLogin
import com.easyhz.placeapp.ui.detail.BoardDetail
import com.easyhz.placeapp.ui.home.search.Search
import com.easyhz.placeapp.ui.navigation.BottomBar
import com.easyhz.placeapp.ui.navigation.HomeSections
import com.easyhz.placeapp.ui.navigation.MainDestinations
import com.easyhz.placeapp.ui.navigation.MainFloatingActionButton
import com.easyhz.placeapp.ui.navigation.PostRoutes.GALLERY
import com.easyhz.placeapp.ui.navigation.UserRoutes.LOGIN
import com.easyhz.placeapp.ui.navigation.addHomeGraph
import com.easyhz.placeapp.ui.navigation.addNewPostGraph
import com.easyhz.placeapp.ui.navigation.addUserGraph
import com.easyhz.placeapp.ui.navigation.rememberMainNavController
import com.easyhz.placeapp.ui.state.ApplicationState
import com.easyhz.placeapp.ui.state.rememberApplicationState
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.util.checkGalleryPermission
import com.easyhz.placeapp.util.login_require

@Composable
fun PlaceApp(
    viewModel: PlaceAppViewModel = hiltViewModel(),
    boardId: Int? = null
) {
    PlaceAppTheme {
        val applicationState = rememberApplicationState()
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
        LaunchedEffect(Unit) {
            boardId?.let { boardId -> mainNavController.navigateToBoardDetailOnDeepLink(boardId) }
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
                            if (needLogin) {
                                applicationState.showSnackBar(login_require)
                            } else {
                                checkGalleryPermission(
                                    context = context,
                                    launcher = launcher,
                                    action = mainNavController::navigateToNewPost
                                )
                            }
                        }
                    )
                }
            },
            snackbarHost = { SnackbarHost(applicationState.snackBarState) }
        ) { paddingValue ->
            NavHost(
                navController = mainNavController.navController,
                startDestination = MainDestinations.HOME_ROUTE,
                modifier = Modifier.padding(paddingValue)
            ) {
                navGraph(
                    applicationState = applicationState,
                    onNavigateToBoardDetail = mainNavController::navigateToBoardDetail,
                    onNavigateToBack = mainNavController::navigateToBack,
                    onNavigateToNext = mainNavController::navigateToNext,
                    getNavBackStack = mainNavController::getNewPostNavBackStack,
                    onNavigateToSearch = mainNavController::navigateToSearch,
                    onNavigateToUser = mainNavController::navigateToUser,
                    onNavigateToHome = mainNavController::navigateToHome,
                    onNavigateToModify = mainNavController::navigateToModify,
                    getFeedDetail = mainNavController::getFeedDetail
                )
            }
        }
    }
}

/**
 * Navigation 관리
 */
private fun NavGraphBuilder.navGraph(
    applicationState: ApplicationState,
    onNavigateToBoardDetail: (Int, NavBackStackEntry) -> Unit,
    onNavigateToBack: () -> Unit,
    onNavigateToNext: () -> Unit,
    getNavBackStack: () -> NavBackStackEntry,
    onNavigateToSearch: (NavBackStackEntry) -> Unit,
    onNavigateToUser: (NavBackStackEntry) -> Unit,
    onNavigateToHome: (NavBackStackEntry, Boolean) -> Unit,
    onNavigateToModify: (FeedDetail, NavBackStackEntry) -> Unit,
    getFeedDetail: () -> FeedDetail?
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.FEED.route
    ) {
        addHomeGraph(
            applicationState = applicationState,
            onNavigateToBoardDetail = onNavigateToBoardDetail,
            onNavigateToSearch = onNavigateToSearch,
            onNavigateToUser = onNavigateToUser
        )
    }
    composable(
        route = "${MainDestinations.BOARD_DETAIL_ROUTE}/{${MainDestinations.BOARD_ID}}",
        arguments = listOf(navArgument(MainDestinations.BOARD_ID) { type = NavType.IntType }),
        deepLinks = listOf(navDeepLink {
            uriPattern = "wooyeojung://board/{${MainDestinations.BOARD_ID}}"
        }),
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val boardId = arguments.getInt(MainDestinations.BOARD_ID)

        BoardDetail(
            id = boardId,
            onNavigateToHome = { onNavigateToHome(backStackEntry, false) },
            onNavigateToModify = { feedDetail ->  onNavigateToModify(feedDetail, backStackEntry) },
            applicationState = applicationState
        )
    }
    navigation(
        route = MainDestinations.NEW_POST_ROUTE,
        startDestination = GALLERY
    ) {
        addNewPostGraph(
            applicationState = applicationState,
            onNavigateToBack = onNavigateToBack,
            onNavigateToNext = onNavigateToNext,
            getNavBackStack = getNavBackStack,
            getFeedDetail = getFeedDetail
        )
    }
    composable(
        route = MainDestinations.SEARCH_ROUTE,
    ) {
        Search(
            onNavigateToBack = onNavigateToBack
        )
    }
    navigation(
        route = MainDestinations.USER_ROUTE,
        startDestination = LOGIN
    ) {
        addUserGraph(
            onNavigateToBack = onNavigateToBack,
            onNavigateToHome = onNavigateToHome,
            applicationState = applicationState
        )
    }
}