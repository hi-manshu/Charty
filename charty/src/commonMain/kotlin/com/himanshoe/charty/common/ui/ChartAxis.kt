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

/**
 * Draws the X axis.
 *
 * @param color The color of the axis.
 * @param stroke The stroke width of the axis.
 */
fun DrawScope.drawXAxis(color: Color, stroke: Float) {
    drawLine(
        start = Offset(0f, 0F),
        end = Offset(0f, size.height),
        color = color,
        strokeWidth = stroke,
    )
}

/**
 * Draws the Y axis.
 *
 * @param color The color of the axis.
 * @param stroke The stroke width of the axis.
 */
fun DrawScope.drawYAxis(color: Color, stroke: Float) {
    drawLine(
        start = Offset(0f, size.height),
        end = Offset(size.width, size.height),
        color = color,
        strokeWidth = stroke
    )
}
