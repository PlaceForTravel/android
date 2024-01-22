package com.easyhz.placeapp.ui.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easyhz.placeapp.R
import com.easyhz.placeapp.ui.home.bookmark.Bookmark
import com.easyhz.placeapp.ui.home.feed.Feed
import com.easyhz.placeapp.ui.home.profile.Profile
import com.easyhz.placeapp.ui.state.ApplicationState
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.util.borderTop

/**
 * BottomNavigation Items
 */
fun NavGraphBuilder.addHomeGraph(
    applicationState: ApplicationState,
    onNavigateToBoardDetail: (Int, NavBackStackEntry) -> Unit,
    onNavigateToSearch: (NavBackStackEntry) -> Unit,
    onNavigateToUser: (NavBackStackEntry) -> Unit
) {
    composable(route = HomeSections.FEED.route) {
        Feed(
            applicationState = applicationState,
            onItemClick = { id -> onNavigateToBoardDetail(id, it)},
            onSearchBarClick = { onNavigateToSearch(it) },
            onNavigateToUser = { onNavigateToUser(it) }
        )
    }
    composable(route = HomeSections.BOOKMARK.route) {
        Bookmark(
            applicationState = applicationState,
            onItemClick = { id -> onNavigateToBoardDetail(id, it) }
        )
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
    FEED(R.string.home_feed, Icons.Outlined.Home, "${MainDestinations.HOME_ROUTE}/feed"),
    BOOKMARK(R.string.home_bookmark, Icons.Outlined.BookmarkBorder, "${MainDestinations.HOME_ROUTE}/bookmark"),
    PROFILE(R.string.home_profile, Icons.Outlined.Person, "${MainDestinations.HOME_ROUTE}/profile")
}

@Composable
fun BottomBar(
    tabs: Array<HomeSections>,
    currentRoute : String,
    onNavigateToRoute: (String) -> Unit,
) {
    val currentTab = tabs.first { it.route == currentRoute }
    NavigationBar(
        containerColor = PlaceAppTheme.colorScheme.mainBackground,
        modifier = Modifier.borderTop(
            color = PlaceAppTheme.colorScheme.primaryBorder,
            width = 1.dp
        )
    ) {
        tabs.forEach { tab ->
            val label = stringResource(id = tab.label)
            NavigationBarItem(
                selected = tab == currentTab,
                onClick = { onNavigateToRoute(tab.route) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = label,
                        modifier = Modifier.size(32.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = PlaceAppTheme.colorScheme.unselectedIcon,
                    unselectedTextColor = PlaceAppTheme.colorScheme.unselectedIcon,
                    selectedIconColor = PlaceAppTheme.colorScheme.selectedIcon,
                    selectedTextColor = PlaceAppTheme.colorScheme.selectedIcon,
                    indicatorColor = PlaceAppTheme.colorScheme.mainBackground
                ),
            )
        }
    }
}


@Preview("default")
@Preview("DarkMode", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewBottomBar() {
    PlaceAppTheme {
        BottomBar(
            tabs = HomeSections.values(),
            currentRoute = "home/feed",
            onNavigateToRoute = { },
        )
    }
}