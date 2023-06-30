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
    val horizontalGridSpacing = height / 5
    val verticalGridSpacing = width / 4

    repeat(4) { i ->
        val y = (i + 1) * horizontalGridSpacing
        val x = (i + 1) * verticalGridSpacing

        drawLine(
            start = Offset(0F, y + spacing),
            end = Offset(width + spacing, y + spacing),
            color = Color.LightGray,
            strokeWidth = 1f
        )
        drawLine(
            start = Offset(x + spacing, 0F),
            end = Offset(x + spacing, height + spacing),
            color = Color.LightGray,
            strokeWidth = 1f
        )
    }
}
