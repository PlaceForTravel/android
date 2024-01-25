package com.easyhz.placeapp.ui.component.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.placeapp.constants.ContentCardIcons
import com.easyhz.placeapp.ui.component.IconText
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape

@Composable
fun CityContent(
    modifier: Modifier = Modifier,
    city: String,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .padding(10.dp)
            .clip(roundShape)
            .background(PlaceAppTheme.colorScheme.mainBackground)
            .clickable { onClick(city) },
    ) {
        IconText(
            modifier = Modifier.padding(20.dp),
            icon = ContentCardIcons.PLACE.icon,
            text = city,
            contentDescription = stringResource(id = ContentCardIcons.PLACE.label)
        )
    }
}

@Preview
@Composable
private fun CityContentPreview() {
    PlaceAppTheme {
        CityContent(city = "전라남도 여수시") {

        }
    }
}