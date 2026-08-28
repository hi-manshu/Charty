package com.himanshoe.charty.radar.internal

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.util.fastForEachIndexed
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

/**
 * Draws each axis value just outside the data point it belongs to.
 *
 * The text follows the vertex rather than the axis label, so it stays attached to the value it
 * reports while the entry animation grows the shape.
 *
 * How far outside is measured, not chosen. The label is pushed along its axis by [shapeClearance] —
 * whatever the shape occupies at that vertex — plus the label's own half-extent in that direction,
 * which is where its box stops. A caller who doubles the font size or the point radius gets the same
 * visual clearance rather than the same number of pixels.
 *
 * Shared by both radar charts: they take the same [RadarLabelConfig], so a value that renders on one
 * and not the other is a bug rather than a difference worth having.
 */
internal fun DrawScope.drawRadarAxisValues(
    points: List<Offset>,
    angles: List<Float>,
    measuredValues: List<TextLayoutResult>,
    shapeClearance: Float,
) {
    measuredValues.fastForEachIndexed { index, textLayoutResult ->
        val point = points.getOrNull(index) ?: return@fastForEachIndexed
        val angle = angles.getOrNull(index) ?: return@fastForEachIndexed
        val halfWidth = textLayoutResult.size.width / 2f
        val halfHeight = textLayoutResult.size.height / 2f
        val halfExtentAlongAxis = abs(cos(angle)) * halfWidth + abs(sin(angle)) * halfHeight
        val distance = shapeClearance + halfExtentAlongAxis
        drawText(
            textLayoutResult = textLayoutResult,
            topLeft =
                Offset(
                    x = point.x + cos(angle) * distance - halfWidth,
                    y = point.y + sin(angle) * distance - halfHeight,
                ),
        )
    }
}
