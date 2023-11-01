package com.easyhz.placeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 *  Destinations .
 */
object MainDestinations {
    const val HOME_ROUTE = "home"
    const val PLACE_DETAIL_ROUTE = "place" //   수정 예정
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

}