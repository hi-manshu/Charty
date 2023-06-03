package com.himanshoe.charty.common.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawXAxis(color: Color, stroke: Float) {
    drawLine(
        start = Offset(0f, 0F),
        end = Offset(0f, size.height),
        color = color,
        strokeWidth = stroke,
    )
}

fun DrawScope.drawYAxis(color: Color, stroke: Float) {
    drawLine(
        start = Offset(0f, size.height),
        end = Offset(size.width, size.height),
        color = color,
        strokeWidth = stroke
    )
}