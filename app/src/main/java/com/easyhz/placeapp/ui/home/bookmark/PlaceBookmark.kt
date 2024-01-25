package com.easyhz.placeapp.ui.home.bookmark

import androidx.compose.foundation.background
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
import com.easyhz.placeapp.ui.component.bookmark.CityContent
import com.easyhz.placeapp.ui.theme.PlaceAppTheme

@Composable
fun PlaceBookmark(
    viewModel: PlaceBookmarkViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchCityList()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PlaceAppTheme.colorScheme.subBackground)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(PlaceAppTheme.colorScheme.subBackground)
        ) {
            items(viewModel.cityList) { city ->
                CityContent(
                    modifier = Modifier.fillMaxWidth(),
                    city = city
                ) {
                    println(it)
                }
            }
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