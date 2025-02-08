package com.himanshoe.charty.common

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

internal fun DrawScope.drawTargetLineIfNeeded(
    canvasWidth: Float,
    targetConfig: TargetConfig,
    yPoint: Float,
) {
    drawLine(
        brush = Brush.linearGradient(targetConfig.targetLineBarColors.value),
        start = Offset(0f, yPoint),
        end = Offset(canvasWidth, yPoint),
        pathEffect = targetConfig.pathEffect,
        strokeWidth = targetConfig.targetStrokeWidth
    )
}

internal fun getDrawingPath(
    individualBarTopLeft: Offset,
    individualBarRectSize: Size,
    cornerRadius: CornerRadius
): Path {
    return Path().apply {
        addRoundRect(
            RoundRect(
                rect = Rect(
                    offset = individualBarTopLeft,
                    size = individualBarRectSize,
                ),
                topLeft = cornerRadius,
                topRight = cornerRadius,
                bottomLeft = CornerRadius.Zero,
                bottomRight = CornerRadius.Zero
            )
        )
    }
}

internal fun getDrawingPath(
    barTopLeft: Offset,
    barRectSize: Size,
    topLeftCornerRadius: CornerRadius,
    topRightCornerRadius: CornerRadius,
    bottomLeftCornerRadius: CornerRadius,
    bottomRightCornerRadius: CornerRadius,
): Path {
    return Path().apply {
        addRoundRect(
            RoundRect(
                rect = Rect(
                    offset = barTopLeft,
                    size = barRectSize,
                ),
                topLeft = topLeftCornerRadius,
                topRight = topRightCornerRadius,
                bottomLeft = bottomLeftCornerRadius,
                bottomRight = bottomRightCornerRadius
            )
        )
    }
}
