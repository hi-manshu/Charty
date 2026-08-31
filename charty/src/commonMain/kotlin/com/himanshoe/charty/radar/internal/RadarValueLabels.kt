package com.himanshoe.charty.radar.internal

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.radar.config.MultipleRadarChartConfig
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

/**
 * Draws one axis's values stacked beneath its label, one row per data set in data-set order.
 *
 * [labelCenterX] and [labelBottom] describe the label as it was actually drawn, so the stack hangs
 * off the real text rather than off where a formula thinks the text is — the two charts align their
 * labels differently, and this helper should not have to know how. The gap between rows is a share
 * of the value's own line height, so a larger [valueTextStyle][com.himanshoe.charty.radar.config.RadarLabelConfig.valueTextStyle]
 * spaces itself out instead of colliding.
 */
internal fun DrawScope.drawRadarValuesBelowLabel(
    labelCenterX: Float,
    labelBottom: Float,
    valuesForAxis: List<TextLayoutResult>,
    gapFraction: Float,
) {
    var top = labelBottom
    valuesForAxis.fastForEachIndexed { _, value ->
        top += value.size.height * gapFraction
        drawText(
            textLayoutResult = value,
            topLeft =
                Offset(
                    x = labelCenterX - value.size.width / 2f,
                    y = top,
                ),
        )
        top += value.size.height
    }
}

/**
 * The anchor to hand [drawRadarValuesBelowLabel] so the painted stack centres on [centerY] when
 * there is no label to hang from.
 *
 * The drawer paints a gap before every row, the first included, so the visible text starts one gap
 * below its anchor; anchoring at `centerY - height / 2` alone leaves the stack sitting half a gap
 * low. The first row's gap is added to the height before halving, which cancels that.
 */
internal fun radarValueStackCenteredAnchor(
    centerY: Float,
    valuesForAxis: List<TextLayoutResult>,
    gapFraction: Float,
): Float {
    var height = 0f
    valuesForAxis.fastForEachIndexed { _, value ->
        height += value.size.height * (1f + gapFraction)
    }
    val leadingGap = valuesForAxis.first().size.height * gapFraction
    return centerY - (height + leadingGap) / 2f
}

/**
 * Draws each axis's values stacked beneath its label, one row per data set in data-set order.
 *
 * The stack hangs off the label box as it was actually drawn — side-aligned, via the same
 * [radarLabelBoxAlignment] the label pass uses. When labels are hidden the values centre themselves on
 * the position the label would have had, matching what the single radar chart does in that case.
 */
internal fun DrawScope.drawMultipleRadarValuesBelowLabels(
    center: Offset,
    maxRadius: Float,
    measuredLabels: List<TextLayoutResult>,
    measuredValues: List<List<TextLayoutResult>>,
    numberOfAxes: Int,
    config: MultipleRadarChartConfig,
    startAngle: Float,
) {
    val labelConfig = config.radarConfig.labelConfig
    val gapFraction = labelConfig.valueGapFraction
    val labelDistance = maxRadius * labelConfig.labelDistanceMultiplier
    for (index in 0 until numberOfAxes) {
        val valuesForAxis =
            measuredValues.mapNotNull { datasetValues ->
                datasetValues.getOrNull(index)
            }
        if (valuesForAxis.isEmpty()) {
            continue
        }
        val angle =
            radarAxisAngleRadians(
                startAngleDegrees = startAngle,
                axisIndex = index,
                numberOfAxes = numberOfAxes,
            )
        val x = center.x + labelDistance * cos(angle)
        val y = center.y + labelDistance * sin(angle)
        val label =
            if (labelConfig.showLabels) {
                measuredLabels.getOrNull(index)
            } else {
                null
            }
        if (label != null) {
            val alignment =
                radarLabelBoxAlignment(
                    angle = angle,
                    textWidth = label.size.width.toFloat(),
                    textHeight = label.size.height.toFloat(),
                )
            drawRadarValuesBelowLabel(
                labelCenterX = x + alignment.x + label.size.width / 2f,
                labelBottom = y + alignment.y + label.size.height,
                valuesForAxis = valuesForAxis,
                gapFraction = gapFraction,
            )
        } else {
            drawRadarValuesBelowLabel(
                labelCenterX = x,
                labelBottom =
                    radarValueStackCenteredAnchor(
                        centerY = y,
                        valuesForAxis = valuesForAxis,
                        gapFraction = gapFraction,
                    ),
                valuesForAxis = valuesForAxis,
                gapFraction = gapFraction,
            )
        }
    }
}
