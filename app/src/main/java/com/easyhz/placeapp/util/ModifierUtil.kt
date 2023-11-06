package com.easyhz.placeapp.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawBorderTop(
    color: Color,
    widthPx: Float,
) {
    drawLine(
        color = color, // 테두리 색상을 원하는 색상으로 변경
        start = Offset(0f, 0f), // 시작점 (왼쪽 상단)
        end = Offset(size.width, 0f), // 끝점 (오른쪽 상단)
        strokeWidth = widthPx // 테두리 두께
    )
}