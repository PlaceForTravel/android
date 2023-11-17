package com.easyhz.placeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.easyhz.placeapp.ui.navigation.PostRoutes.GALLERY

/**
 *  Destinations .
 */
object MainDestinations {
    const val HOME_ROUTE = "home"
    const val BOARD_DETAIL_ROUTE = "detail" // Detail
    const val BOARD_ID = "boardId" // Detail
    const val NEW_POST_ROUTE = "post"
}

/**
 * NavController instance 생성 및 상태 저장
 */
@Composable
fun rememberMainNavController(
    navController: NavHostController = rememberNavController()
): MainNavController = remember(navController) {
    MainNavController(navController)
}

/**
 * UI Navigation Logic
 */
@Stable
class MainNavController(
    val navController: NavHostController,
) {
    val currentRoute: String?
        get() = navController.currentDestination?.route
    fun navigateToBottomBarRoute(route: String) {
        if(route != currentRoute) {
            navController.navigate(route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop  = true
                restoreState = true
            }
        }
    }

    fun navigateToBoardDetail(boardId: Int, from: NavBackStackEntry) {
        // 중복 이벤트 발생할 수도 있기 때문에 체크
        if(from.isResumed()) navController.navigate("${MainDestinations.BOARD_DETAIL_ROUTE}/$boardId")
    }

    fun navigateToNewPost() {
        navController.navigate("${MainDestinations.NEW_POST_ROUTE}/$GALLERY")
    }

    fun navigateToBack() {
        navController.popBackStack()
    }

    fun navigateToNext() {
        val next = when(currentRoute) {
            NewPostOrder.GALLERY.route -> NewPostOrder.NEW_POST.route
            NewPostOrder.NEW_POST.route -> NewPostOrder.COMPLETE.route
            else -> NewPostOrder.COMPLETE.route
        }
        navController.navigate(next)
    }

}

enum class NewPostOrder(
    val route: String
) {
    GALLERY(route = "${MainDestinations.NEW_POST_ROUTE}/${PostRoutes.GALLERY}"),
    NEW_POST(route = "${MainDestinations.NEW_POST_ROUTE}/${PostRoutes.NEW_POST}"),
    COMPLETE(route = HomeSections.FEED.route)
}

private fun NavBackStackEntry.isResumed() = this.getLifecycle().currentState == Lifecycle.State.RESUMED