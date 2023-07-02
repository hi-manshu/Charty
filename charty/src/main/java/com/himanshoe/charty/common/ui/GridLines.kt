/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.common.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawGridLines(width: Float, height: Float, spacing: Float) {
    // Calculate the dimensions of the grid area by subtracting the spacing
    val newHeight = height.minus(spacing)
    val newWidth = width.minus(spacing)

    // Calculate the spacing between horizontal and vertical grid lines
    val horizontalGridSpacing = newHeight / 5
    val verticalGridSpacing = newWidth / 4

    // Draw horizontal grid lines
    repeat(4) { i ->
        val y = (i + 1) * horizontalGridSpacing

        drawLine(
            start = Offset(0F, y + spacing),
            end = Offset(newWidth + spacing, y + spacing),
            color = Color.LightGray,
            strokeWidth = 1f
        )
    }

    // Draw vertical grid lines
    repeat(3) { i ->
        val x = (i + 1) * verticalGridSpacing

        drawLine(
            start = Offset(x + spacing, 0F),
            end = Offset(x + spacing, newHeight + spacing),
            color = Color.LightGray,
            strokeWidth = 1f
        )
    }
}
