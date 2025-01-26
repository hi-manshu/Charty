package com.himanshoe.charty.common

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect

/**
 * Extension function to draw axis lines on a canvas.
 *
 * @param hasNegativeValues A boolean indicating whether the data contains negative values.
 * @return A Modifier with the axis lines drawn.
 */
fun Modifier.drawAxisLine(hasNegativeValues: Boolean = true, axisLineColor: Color): Modifier {
    return this.drawWithCache {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val yAxis = if (hasNegativeValues) {
            canvasHeight / 2
        } else {
            canvasHeight
        }
        onDrawBehind {
            // Draw the x-axis
            drawLine(
                color = axisLineColor,
                start = Offset(0f, yAxis),
                end = Offset(canvasWidth, yAxis),
                strokeWidth = 2f
            )

            // Draw the y-axis
            drawLine(
                color = axisLineColor,
                start = Offset(0f, 0f),
                end = Offset(0f, canvasHeight),
                strokeWidth = 2f
            )
        }
    }
}


/**
 * Extension function to draw range lines on a canvas.
 *
 * @param hasNegativeValues A boolean indicating whether the data contains negative values.
 * @return A Modifier with the range lines drawn.
 */
fun Modifier.drawRangeLines(hasNegativeValues: Boolean = true, rangeLineColor: Color): Modifier {
    return this.drawWithCache {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val yAxis = if (hasNegativeValues) {
            canvasHeight / 2
        } else {
            canvasHeight
        }
        val rangeLineCount = 3
        val rangeLineSpacing = canvasHeight / (rangeLineCount * 2 + 1)
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

        onDrawBehind {
            // Draw range lines
            if (hasNegativeValues) {
                for (i in 1..rangeLineCount) {
                    val yOffsetTop = yAxis - i * rangeLineSpacing
                    val yOffsetBottom = yAxis + i * rangeLineSpacing
                    drawLine(
                        color = rangeLineColor,
                        start = Offset(0f, yOffsetTop),
                        end = Offset(canvasWidth, yOffsetTop),
                        strokeWidth = 1f,
                        pathEffect = pathEffect
                    )
                    drawLine(
                        color = rangeLineColor,
                        start = Offset(0f, yOffsetBottom),
                        end = Offset(canvasWidth, yOffsetBottom),
                        strokeWidth = 1f,
                        pathEffect = pathEffect
                    )
                }
            } else {
                val totalLines = 4
                val spacing = canvasHeight / (totalLines + 1)
                for (i in 1..totalLines) {
                    val yOffset = i * spacing
                    drawLine(
                        color = rangeLineColor,
                        start = Offset(0f, yOffset),
                        end = Offset(canvasWidth, yOffset),
                        strokeWidth = 1f,
                        pathEffect = pathEffect
                    )
                }
            }
        }
    }
}