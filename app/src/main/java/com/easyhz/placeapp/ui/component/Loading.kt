package com.easyhz.placeapp.ui.component

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.easyhz.placeapp.ui.theme.PlaceAppTheme

@Composable
fun CircularLoading(
    scope: BoxScope
) {
    scope.apply {
        Box(modifier = Modifier
            .align(Alignment.Center)
            .zIndex(10F)) {
            CircularProgressIndicator(
                color = PlaceAppTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun SpinningProgressBar(
    modifier: Modifier = Modifier,
    canvasSize: Int = 48,
) {
    val count = 10
    val coefficient = 360f / count
    val infiniteTransition = rememberInfiniteTransition(label = "SpinningProgressBar")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = count.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(count * 80, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "SpinningProgressBar"
    )

    Canvas(modifier = modifier.size(canvasSize.dp)) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val width = size.width * .3f
        val height = size.height / 8
        val topLeft = Offset(canvasWidth - width, (canvasHeight - height) / 2)
        val roundRectSize = Size(width, height)
        val cornerRadius = width.coerceAtMost(height) / 2
        for (i in 0..360 step 360 / count) {
            drawRoundRect(
                degrees = i.toFloat(),
                color = Color.LightGray.copy(alpha = .7f),
                topLeft = topLeft,
                size = roundRectSize,
                cornerRadius = cornerRadius
            )
        }
        for (i in 1..4) {
            drawRoundRect(
                degrees = (angle.toInt() + i) * coefficient,
                color = Color.Gray.copy(alpha = (0.2f + 0.2f * i).coerceIn(0f, 1f)),
                topLeft = topLeft,
                size = roundRectSize,
                cornerRadius = cornerRadius
            )
        }
    }
}
private fun DrawScope.drawRoundRect(
    degrees: Float,
    color: Color,
    topLeft: Offset,
    size: Size,
    cornerRadius: Float
) {
    rotate(degrees) {
        drawRoundRect(
            color = color,
            topLeft = topLeft,
            size = size,
            cornerRadius = CornerRadius(cornerRadius, cornerRadius)
        )
    }
}
@Preview(name = "Default")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SpinningProgressBarPreview() {
    PlaceAppTheme {
        SpinningProgressBar()
    }
}