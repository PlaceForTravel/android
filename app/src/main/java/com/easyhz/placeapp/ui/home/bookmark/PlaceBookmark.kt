package com.easyhz.placeapp.ui.home.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.easyhz.placeapp.domain.model.bookmark.place.SavedPlaceItem.Companion.toLatLngType
import com.easyhz.placeapp.ui.component.bookmark.CityContent
import com.easyhz.placeapp.ui.component.bookmark.NoContent
import com.easyhz.placeapp.ui.component.map.NaverMap
import com.easyhz.placeapp.ui.theme.PlaceAppTheme

@Composable
fun PlaceBookmark(
    viewModel: PlaceBookmarkViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchPlaceList()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            NaverMap(places = viewModel.placeList.toLatLngType(), type = 1)
//            when(viewModel.cityList.size) {
//                0 -> NoContent(scope = this, type = BookmarkTabs.PLACE)
//                else -> LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(PlaceAppTheme.colorScheme.subBackground)
//                ) {
//                    items(viewModel.cityList) { city ->
//                        CityContent(
//                            modifier = Modifier.fillMaxWidth(),
//                            city = city
//                        ) {
//                            println(it)
//                        }
//                    }
//                }
//            }
        }
    }
}

@Preview
@Composable
private fun PlaceBookmarkPreview() {
    PlaceAppTheme {
        PlaceBookmark()
    }
}