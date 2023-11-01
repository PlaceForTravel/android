package com.easyhz.placeapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.easyhz.placeapp.ui.navigation.BottomBar
import com.easyhz.placeapp.ui.navigation.HomeSections
import com.easyhz.placeapp.ui.navigation.MainDestinations
import com.easyhz.placeapp.ui.navigation.addHomeGraph
import com.easyhz.placeapp.ui.navigation.rememberMainNavController
import com.easyhz.placeapp.ui.theme.PlaceAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceApp() {
    PlaceAppTheme {
        val mainNavController = rememberMainNavController()
        mainNavController.navController.currentBackStackEntryAsState().value?.destination
        Scaffold(
            bottomBar = {
                BottomBar(
                    tabs = HomeSections.values(),
                    currentRoute = mainNavController.currentRoute ?: HomeSections.FEED.route,
                    onNavigateToRoute = mainNavController::navigateToBottomBarRoute
                )
            },
        ) { paddingValue ->
            NavHost(
                navController = mainNavController.navController,
                startDestination = MainDestinations.HOME_ROUTE,
                modifier = Modifier.padding(paddingValue)
            ) {
                navGraph()
            }
        }
    }
}

/**
 * Navigation 관리
 */
private fun NavGraphBuilder.navGraph() {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.FEED.route
    ) {
        addHomeGraph()
    }
}