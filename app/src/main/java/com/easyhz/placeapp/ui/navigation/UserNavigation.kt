package com.easyhz.placeapp.ui.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easyhz.placeapp.ui.state.ApplicationState
import com.easyhz.placeapp.ui.user.Login

fun NavGraphBuilder.addUserGraph(
    onNavigateToBack: () -> Unit,
    onNavigateToHome: (NavBackStackEntry, Boolean) -> Unit,
    applicationState: ApplicationState
) {
    composable(
        route = "${MainDestinations.USER_ROUTE}/${UserRoutes.LOGIN}"
    ) {
        Login(
            onNavigateToBack = onNavigateToBack,
            onNavigateToHome = { onNavigateToHome(it, true) },
            applicationState = applicationState
        )
    }
}