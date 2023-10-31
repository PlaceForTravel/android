package com.easyhz.placeapp.ui.home.feed

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.easyhz.placeapp.ui.navigation.BottomBar
import com.easyhz.placeapp.ui.navigation.HomeSections

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Feed(
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface {
        Scaffold(
            bottomBar = {
                BottomBar(
                    tabs = HomeSections.values(),
                    currentRoute = HomeSections.FEED.route,
                    onNavigateToRoute = onNavigateToRoute
                )
            },
            modifier = modifier
        ) { paddingValues ->
            Text(text = "Feed", modifier = Modifier.padding(paddingValues))
        }
    }
}

@Preview
@Composable
private fun FeedPreview() {
    Feed(
        onNavigateToRoute = { },
        modifier = Modifier
    )
}