package com.easyhz.placeapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.easyhz.placeapp.ui.navigation.HomeSections
import com.easyhz.placeapp.ui.navigation.MainDestinations
import com.easyhz.placeapp.ui.navigation.addHomeGraph
import com.easyhz.placeapp.ui.navigation.rememberMainNavController
import com.easyhz.placeapp.ui.theme.PlaceAppTheme

@Composable
fun PlaceApp() {
    PlaceAppTheme {
        val mainNavController = rememberMainNavController()
        NavHost(
            navController = mainNavController.navController,
            startDestination = MainDestinations.HOME_ROUTE) {

            navGraph(
                onNavigateToRoute = mainNavController::navigateToBottomBarRoute
            )
        }
    }
}

/**
 * Navigation 관리
 */
private fun NavGraphBuilder.navGraph(
    onNavigateToRoute: (String) -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.FEED.route
    ) {
        addHomeGraph(onNavigateToRoute)
    }
}