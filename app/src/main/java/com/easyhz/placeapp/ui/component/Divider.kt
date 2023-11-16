package com.easyhz.placeapp.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.placeapp.ui.theme.PlaceAppTheme

@Composable
fun SpaceDivider(padding: Int) {
    Spacer(modifier = Modifier.padding(padding.dp))
}

@Composable
fun CircleDivider(
    radius: Int,
    color: Color = PlaceAppTheme.colorScheme.subText,
    containerSize: Int = 36,
){
    Canvas(
        modifier = Modifier
            .size(containerSize.dp)
            .background(Color.Transparent)
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        drawCircle(
            color = color,
            center = Offset(centerX, centerY),
            radius = radius.dp.toPx(),
        )
    }
}

@Composable
@Preview
private fun CircleDividerPreview() {
    CircleDivider(radius = 3, containerSize = 36)
}