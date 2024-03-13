package com.easyhz.placeapp.ui.component.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.placeapp.R
import com.easyhz.placeapp.constants.ContentCardIcons
import com.easyhz.placeapp.domain.model.feed.detail.PlaceImagesItem
import com.easyhz.placeapp.ui.component.SimpleIconButton
import com.easyhz.placeapp.ui.component.map.LatLngType
import com.easyhz.placeapp.ui.component.map.NaverMap
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape

@Composable
fun MapModal(
    modifier: Modifier = Modifier,
    item: PlaceImagesItem,
    places: List<LatLngType>,
    isViewAll: Boolean,
    onViewAllClick: () -> Unit,
    onClose: () -> Unit,
    onSaved: (Int) -> Unit,
) {
    Card(
        modifier = modifier.padding(30.dp),
    ) {
        Column(
            modifier = Modifier
                .background(color = PlaceAppTheme.colorScheme.mainBackground)
                .clip(roundShape)
        ) {
            MapModalHeader(
                placeName = item.placeName,
                isViewAll = isViewAll,
                isLike = item.like,
                onClose = onClose,
                onSaved = { onSaved(item.placeId) }
            )
            Box(modifier = modifier) {
                if (isViewAll) {
                    NaverMap(modifier = modifier, places = places)
                } else {
                    NaverMap(modifier = modifier, places = listOf(LatLngType(item.latitude, item.longitude)))
                }
                Button(
                    onClick = onViewAllClick,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 20.dp)
                        .width(200.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PlaceAppTheme.colorScheme.secondary, contentColor = PlaceAppTheme.colorScheme.subBackground),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(stringResource(id = if (isViewAll) R.string.view_one_place else R.string.view_all_place))
                }
            }
        }

    }
}
@Composable
private fun MapModalHeader(
    placeName: String,
    isViewAll: Boolean,
    isLike: Boolean,
    onClose: () -> Unit,
    onSaved: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .padding(top = 10.dp),
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            if (!isViewAll) {
                SimpleIconButton(
                    icon = if (isLike) ContentCardIcons.BOOKMARK_FILLED.icon else ContentCardIcons.BOOKMARK.icon,
                    contentDescription = stringResource(id = ContentCardIcons.BOOKMARK.label),
                    modifier = Modifier.size(30.dp),
                    onClick = onSaved
                )
            }
        }
        Box(
            modifier = Modifier.weight(2f),
            contentAlignment = Alignment.Center
        ) {
            Text(if (isViewAll) { stringResource(id = R.string.view_all_place) } else { placeName })
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = stringResource(id = R.string.user_close),
                modifier = Modifier.clickable {
                    onClose()
                }
            )
        }
    }
}

@Composable
fun WindowShade(alpha: Float = 0.5f) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = alpha))
    )
}

@Preview
@Composable
private fun MapBottomSheetHeaderPreview() {
    PlaceAppTheme {
        MapModalHeader("카카오 판교아지트", false, false, { }) {  }
    }
}