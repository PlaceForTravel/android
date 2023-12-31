package com.easyhz.placeapp.ui.component.post

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.FmdBad
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.easyhz.placeapp.R
import com.easyhz.placeapp.domain.model.post.Place
import com.easyhz.placeapp.ui.component.IconText
import com.easyhz.placeapp.ui.component.ImageLoader
import com.easyhz.placeapp.ui.component.ImageSlider
import com.easyhz.placeapp.ui.component.SimpleIconButton
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape
import com.easyhz.placeapp.util.getImageRequestDefault


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesContent(
    modifier: Modifier = Modifier,
    imageSize: Dp,
    contents : List<Place>,
    pagerState: PagerState,
    onPlaceClick: () -> Unit
) {
    ImageSlider(pagerState = pagerState, itemsCount = contents.size, modifier = modifier) { index ->
        Column(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            val imageRequest = getImageRequestDefault(contents[index].imageFile, LocalContext.current, 100)
            ImageLoader(
                image = imageRequest,
                contentDescription = contents[index].imageName,
                modifier = Modifier
                    .size(imageSize)
                    .clip(roundShape)
            )
            PlaceContent(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clip(roundShape)
                    .border(
                        width = 1.dp,
                        color = contents[index].placeBorderColor,
                        shape = roundShape
                    ),
                place = contents[index].placeName ?: stringResource(id = R.string.post_add_place),
                onClick = onPlaceClick
            )
        }
    }
}

@Composable
fun PlaceContent(
    modifier: Modifier = Modifier,
    place: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.clickable {
            onClick()
        }
    ){
        Row {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
            ) {
                if (place == stringResource(id = R.string.post_add_place)) {
                    IconText(
                        icon = Icons.Outlined.FmdBad,
                        iconSize = 24.dp,
                        tint = PlaceAppTheme.colorScheme.error,
                        text = place,
                        contentDescription = "No_Place_Added",
                        onClick = { onClick() }
                    )
                } else {
                    Text(text = "📍  $place")
                }

            }
            Box(
                modifier = Modifier.padding(10.dp)
            ) {
                SimpleIconButton(
                    modifier = Modifier.size(24.dp),
                    icon = Icons.Outlined.ArrowForwardIos,
                    onClick = { onClick() }
                )
            }
        }
    }
}

@Composable
fun TextContent(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean
) {
    Box() {
        TextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            placeholder = { Text(text = stringResource(id = R.string.post_add_text))},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = PlaceAppTheme.colorScheme.transparent,
                unfocusedContainerColor = PlaceAppTheme.colorScheme.transparent,
                disabledContainerColor = PlaceAppTheme.colorScheme.transparent,
                cursorColor = PlaceAppTheme.colorScheme.mainText,
                focusedIndicatorColor = PlaceAppTheme.colorScheme.transparent,
                unfocusedIndicatorColor = PlaceAppTheme.colorScheme.transparent,
            ),
        )
    }
}


/**
 * PREVIEW
 */

@Preview
@Composable
private fun PlaceContentPreview() {
    PlaceAppTheme {
        Column {
            PlaceContent(place = "장소를 추가해 주세요.", onClick = { })
            PlaceContent(place = "런던 베이글 제주점", onClick = { })

        }
    }
}
@Preview
@Composable
private fun TextContentPreview() {
    PlaceAppTheme {
        TextContent(
            modifier = Modifier
                .height(300.dp)
                .border(1.dp, color = Color.Black, shape = roundShape),
            value = "", onValueChange = { }, enabled = false
        )
    }
}