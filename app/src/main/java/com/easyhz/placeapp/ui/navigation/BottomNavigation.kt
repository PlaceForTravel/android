package com.easyhz.placeapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easyhz.placeapp.R
import com.easyhz.placeapp.ui.home.feed.Feed
import com.easyhz.placeapp.ui.home.profile.Profile

/**
 * BottomNavigation Items
 */
fun NavGraphBuilder.addHomeGraph() {
    composable(route = HomeSections.FEED.route) {
        Feed()
    }
    composable(route = HomeSections.PROFILE.route) {
        Profile()
    }
}

enum class HomeSections(
    @StringRes val label: Int,
    val icon: ImageVector,
    val route: String
) {
    FEED(R.string.home_feed, Icons.Outlined.Home, "home/feed"),
    PROFILE(R.string.home_profile, Icons.Outlined.Person, "home/profile")
}

@Composable
fun BottomBar(
    tabs: Array<HomeSections>,
    currentRoute : String,
    onNavigateToRoute: (String) -> Unit,
) {
    val currentTab = tabs.first { it.route == currentRoute }
    NavigationBar() {
        tabs.forEach { tab ->
            val label = stringResource(id = tab.label)
            NavigationBarItem(
                label = {
                    Text(text = label)
                },
                selected = tab == currentTab,
                onClick = { onNavigateToRoute(tab.route) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = label
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Color.DarkGray,
                    selectedIconColor = Color.Black
                )
            )
        }
    }
}


@Preview
@Composable
private fun PreviewBottomBar() {
    BottomBar(
        tabs = HomeSections.values(),
        currentRoute = "home/feed",
        onNavigateToRoute = { },
    )
}