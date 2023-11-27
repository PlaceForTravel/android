package com.easyhz.placeapp.ui.component.detail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.easyhz.placeapp.constants.ContentCardIcons
import com.easyhz.placeapp.ui.component.SimpleIconButton
import com.easyhz.placeapp.ui.component.map.NaverMap
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape

@Composable
fun MapModal(
    modifier: Modifier = Modifier,
    context: Context,
) {
    Card(
        modifier = modifier.padding(30.dp),
    ) {
        Column(
            modifier = Modifier
                .background(color = PlaceAppTheme.colorScheme.mainBackground)
                .clip(roundShape)
        ) {
            MapModalHeader()
            NaverMap(
                context = context,
                modifier = modifier
            )
        }

    }
}
@Composable
private fun MapModalHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .padding(top = 10.dp),
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            SimpleIconButton(
                icon = ContentCardIcons.BOOKMARK.icon,
                contentDescription = stringResource(id = ContentCardIcons.BOOKMARK.label),
                modifier = Modifier.size(30.dp),
                onClick = { }
            )
        }
        Box(
            modifier = Modifier.weight(2f),
            contentAlignment = Alignment.Center
        ) {
            Text("당근")
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text("모아보기")
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
        MapModalHeader()
    }
}