package com.easyhz.placeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.easyhz.placeapp.domain.model.feed.detail.FeedDetail
import com.easyhz.placeapp.ui.navigation.PostRoutes.GALLERY
import com.easyhz.placeapp.ui.navigation.PostRoutes.MODIFY
import com.easyhz.placeapp.ui.navigation.UserRoutes.LOGIN

/**
 *  Destinations .
 */
object MainDestinations {
    const val HOME_ROUTE = "home"
    const val BOARD_DETAIL_ROUTE = "detail" // Detail
    const val BOARD_ID = "boardId" // Detail
    const val NEW_POST_ROUTE = "post"
    const val SEARCH_ROUTE = "search"
    const val USER_ROUTE = "user" // user
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

    fun navigateToUser(from: NavBackStackEntry) {
        if (from.isResumed()) navController.navigate("${MainDestinations.USER_ROUTE}/$LOGIN")
    }

    fun navigateToHome(from: NavBackStackEntry) {
        if (from.isResumed()) navController.navigate("${MainDestinations.HOME_ROUTE}/feed")
    }

    fun navigateToModify(feedDetail: FeedDetail, from: NavBackStackEntry) {
        navController.currentBackStackEntry?.savedStateHandle?.set(key = "feedDetail", value = feedDetail)
        if (from.isResumed()) navController.navigate("${MainDestinations.NEW_POST_ROUTE}/$MODIFY")
    }

    fun navigateToBoardDetail(boardId: Int, from: NavBackStackEntry) {
        // 중복 이벤트 발생할 수도 있기 때문에 체크
        if(from.isResumed()) navController.navigate("${MainDestinations.BOARD_DETAIL_ROUTE}/$boardId")
    }

    fun navigateToSearch(from: NavBackStackEntry) {
        if (from.isResumed()) navController.navigate(MainDestinations.SEARCH_ROUTE)
    }

    fun navigateToNewPost() {
        navController.navigate("${MainDestinations.NEW_POST_ROUTE}/$GALLERY")
    }

    fun navigateToBack() {
        navController.popBackStack()
    }

    fun navigateToNext() {
        val next = getNextNewPostOrder()

        if (next == NewPostOrder.COMPLETE.route) {
            navController.popBackStack(NewPostOrder.COMPLETE.route, true)
        }
        navController.navigate(next)
    }

    fun getNewPostNavBackStack(): NavBackStackEntry = navController.getBackStackEntry(getBeforeNewPostOrder())

    fun getFeedDetail(): FeedDetail? = getBackStackBundle("feedDetail", FeedDetail::class.java)

    private fun <T> getBackStackBundle(name: String, m: Class<T>): T? {
        val value = navController.previousBackStackEntry?.savedStateHandle?.get<T>(name)
        return if(m.isInstance(value)) value else null
    }

    private fun getNextNewPostOrder(): String {
        return getNextOrBeforeNewPostOrder(true)
    }

    private fun getBeforeNewPostOrder(): String {
        return getNextOrBeforeNewPostOrder(false)
    }

    /**
     *  제한 범위 : NewPostOrder
     *  @param isNext next = true, before = false
     *  @return route 반환
     */
    private fun getNextOrBeforeNewPostOrder(isNext: Boolean): String {
        val orders = NewPostOrder.values()

        if (currentRoute.equals("${MainDestinations.NEW_POST_ROUTE}/$MODIFY")) return orders.last().route

        val currentIndex = orders.indexOfFirst { it.route == currentRoute }
        val targetIndex = if (isNext) currentIndex + 1 else currentIndex - 1
        /* `coerceIn` - 값이 범위 안에 있으면 해당 값을, 값이 범위 안에 없으면 경계 값을 리턴 */
        val validIndex = targetIndex.coerceIn(orders.indices)

        return orders[validIndex].route
    }
}

private fun NavBackStackEntry.isResumed() = this.getLifecycle().currentState == Lifecycle.State.RESUMED