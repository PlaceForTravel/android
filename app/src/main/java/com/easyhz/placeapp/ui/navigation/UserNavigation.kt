package com.easyhz.placeapp.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easyhz.placeapp.ui.user.Login

fun NavGraphBuilder.addUserGraph(
    onNavigateToBack: () -> Unit
) {
    composable(
        route = "${MainDestinations.USER_ROUTE}/${UserRoutes.LOGIN}"
    ) {
        Login(onNavigateToBack = onNavigateToBack)
    }
}