package com.easyhz.placeapp.ui.home.bookmark

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.placeapp.R
import com.easyhz.placeapp.ui.component.SpaceDivider
import com.easyhz.placeapp.ui.component.post.BookmarkHeader
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class BookmarkTabs(
    @StringRes val text: Int,
) {
    POST(R.string.bookmark_tabs_post) {
        @Composable
        override fun Screen(onItemClick: (Int) -> Unit) {
            PostBookmark(onItemClick = onItemClick)
        }
    },
    PLACE(R.string.bookmark_tabs_place) {
        @Composable
        override fun Screen(onItemClick: (Int) -> Unit) {
            PlaceBookmark()
        }
    };

    @Composable
    abstract fun Screen(onItemClick: (Int) -> Unit)
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Bookmark(
    onItemClick: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { BookmarkTabs.values().size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    Column {
        BookmarkHeader()
        Tabs(tabIndex = selectedTabIndex.value, scope = scope, pagerState = pagerState)
        HorizontalPager(state = pagerState) {
            BookmarkTabs.values()[it].Screen(onItemClick = onItemClick)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Tabs(
    tabIndex: Int,
    scope: CoroutineScope,
    pagerState: PagerState
) {
    LazyRow {
        itemsIndexed(BookmarkTabs.values()) { index, item ->
            Row {
                if (index == 0) {
                    SpaceDivider(padding = 5)
                }
                TabButton(
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp),
                    selected = tabIndex == index,
                    text = stringResource(id = item.text)
                ) {
                    scope.launch {
                        pagerState.animateScrollToPage(item.ordinal)
                    }
                }
            }
        }
    }
}

@Composable
private fun TabButton(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    text: String,
    onClick: () -> Unit
) {
    val containerColor = if (selected) PlaceAppTheme.colorScheme.selectedIcon else PlaceAppTheme.colorScheme.transparent
    val contentColor = if (selected) PlaceAppTheme.colorScheme.subBackground else PlaceAppTheme.colorScheme.secondaryBorder
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = contentColor),
        border = BorderStroke(width = 1.dp, color = PlaceAppTheme.colorScheme.secondaryBorder),
        onClick = onClick
    ) {
        Text(text = text)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun TabsPreview() {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { BookmarkTabs.values().size })
    PlaceAppTheme {
        Tabs(tabIndex = 1, scope = scope, pagerState = pagerState)
    }
}