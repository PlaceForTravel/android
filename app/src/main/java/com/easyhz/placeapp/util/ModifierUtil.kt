package com.easyhz.placeapp.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp


fun Modifier.borderTop(
    color: Color,
    width: Dp
): Modifier {
    return this.drawBehind {
        drawBorder(
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            color = color,
            widthPx = width.toPx()
        )
    }
}


fun Modifier.borderBottom(
    color: Color,
    width: Dp
): Modifier {
    return this.drawBehind {
        drawBorder(
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            color = color,
            widthPx = width.toPx()
        )
    }
}

fun DrawScope.drawBorder(
    start: Offset,
    end: Offset,
    color: Color,
    widthPx: Float,
) {
    drawLine(
        color = color, // 테두리 색상을 원하는 색상으로 변경
        start = start, // 시작점 (왼쪽 상단)
        end = end, // 끝점 (오른쪽 상단)
        strokeWidth = widthPx // 테두리 두께
    )
}