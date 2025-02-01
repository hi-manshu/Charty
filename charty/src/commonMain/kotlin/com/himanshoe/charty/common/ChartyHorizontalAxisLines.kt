package com.himanshoe.charty.common

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color


internal fun Modifier.drawYAxisLineForHorizontalChart(
    hasNegativeValues: Boolean,
    allNegativeValues: Boolean,
    allPositiveValues: Boolean,
    axisLineColor: Color,
): Modifier = this.drawWithCache {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val xAxis =
        if (hasNegativeValues && !allNegativeValues && !allPositiveValues) canvasWidth / 2 else 0f

    onDrawBehind {
        // Draw the y-axis
        drawLine(
            color = axisLineColor,
            start = Offset(xAxis, 0f),
            end = Offset(xAxis, canvasHeight),
            strokeWidth = 2f,
        )
    }

}


internal fun Modifier.drawRangeLineForHorizontalChart(
    allNegativeValues: Boolean,
    allPositiveValues: Boolean,
    axisLineColor: Color,
): Modifier = this.drawWithCache {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val dashLength = 10f
    val dashGap = 10f

    onDrawBehind {
        // Draw vertical dashed lines
        val dashCount = 5
        val dashSpacing = canvasWidth / (dashCount + 1)
        val drawDashedLine: (Float) -> Unit = { x ->
            var currentY = 0f
            while (currentY < canvasHeight) {
                drawLine(
                    color = axisLineColor,
                    start = Offset(x, currentY),
                    end = Offset(x, currentY + dashLength),
                    strokeWidth = 2f,
                )
                currentY += dashLength + dashGap
            }
        }

        if (allNegativeValues || allPositiveValues) {
            val centerX = canvasWidth / 2
            drawDashedLine(centerX - 2 * dashSpacing)
            drawDashedLine(centerX - dashSpacing)
            drawDashedLine(centerX)
            drawDashedLine(centerX + dashSpacing)
            drawDashedLine(centerX + 2 * dashSpacing)
        } else {
            val centerX = canvasWidth / 2
            drawDashedLine(centerX - dashSpacing)
            drawDashedLine(centerX - 2 * dashSpacing)
            drawDashedLine(centerX)
            drawDashedLine(centerX + dashSpacing)
            drawDashedLine(centerX + 2 * dashSpacing)
        }
    }
}