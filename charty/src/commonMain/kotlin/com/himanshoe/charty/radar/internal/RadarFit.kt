package com.himanshoe.charty.radar.internal

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.radar.config.MultipleRadarChartConfig
import com.himanshoe.charty.radar.config.RadarChartConfig
import com.himanshoe.charty.radar.config.RadarLabelConfig
import com.himanshoe.charty.radar.config.RadarValuePlacement
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

private const val COEFFICIENT_EPSILON = 1e-4f

/**
 * The floor on how far fitting may shrink the radar, as a fraction of the padded radius. Without it
 * a pathological label — a paragraph on one axis — could shrink the chart to a dot; past this point
 * clipping the label is the lesser harm, and the caller's remedies are shorter labels or more room.
 */
private const val MIN_FIT_FRACTION = 0.4f

/**
 * One axis's text as the fit solver sees it: a box hung off the label anchor, where the anchor sits
 * at `radius * labelDistanceMultiplier` along [angleRadians] from the centre. [offsetX]/[offsetY]
 * are the box's top-left relative to the anchor and do not depend on the radius; [height] includes
 * any value stack hanging below the label.
 */
internal class RadarFitBox(
    val angleRadians: Float,
    val offsetX: Float,
    val offsetY: Float,
    val width: Float,
    val height: Float,
)

/**
 * The largest radius at which every [RadarFitBox] stays inside the canvas, capped at [paddedRadius].
 *
 * Each box's position is `centre + radius * multiplier * (cos, sin) + offset`, which is linear in
 * the radius — so "stay inside the canvas" is one linear inequality per edge, and the answer is the
 * smallest of their solutions. A chart whose labels already fit gets [paddedRadius] back unchanged,
 * which is what keeps existing renders byte-identical: fitting only ever shrinks, and only when
 * something would otherwise leave the canvas.
 */
internal fun radarFitRadius(
    centerX: Float,
    centerY: Float,
    canvasWidth: Float,
    canvasHeight: Float,
    labelDistanceMultiplier: Float,
    boxes: List<RadarFitBox>,
    paddedRadius: Float,
): Float {
    var fit = paddedRadius
    boxes.forEach { box ->
        val coefficientX = labelDistanceMultiplier * cos(box.angleRadians)
        val coefficientY = labelDistanceMultiplier * sin(box.angleRadians)
        fit =
            minOf(
                fit,
                maxRadiusFor(
                    base = centerX + box.offsetX,
                    coefficient = coefficientX,
                    low = 0f,
                    high = canvasWidth - box.width,
                ),
                maxRadiusFor(
                    base = centerY + box.offsetY,
                    coefficient = coefficientY,
                    low = 0f,
                    high = canvasHeight - box.height,
                ),
            )
    }
    return maxOf(fit, paddedRadius * MIN_FIT_FRACTION)
}

/**
 * The largest radius keeping `base + radius * coefficient` inside `[low, high]`. A coefficient near
 * zero means the radius cannot move this edge of the box at all, so it imposes no bound.
 */
private fun maxRadiusFor(
    base: Float,
    coefficient: Float,
    low: Float,
    high: Float,
): Float =
    when {
        coefficient > COEFFICIENT_EPSILON -> (high - base) / coefficient
        coefficient < -COEFFICIENT_EPSILON -> (base - low) / -coefficient
        else -> Float.MAX_VALUE
    }

/**
 * The radius the single radar draws at: the padding-derived radius, shrunk just enough for every
 * centred label — and any value stack beneath it — to stay inside the canvas when `scaleToFit` asks
 * for that.
 */
internal fun DrawScope.radarChartFitRadius(
    config: RadarChartConfig,
    measuredLabels: List<TextLayoutResult>,
    measuredValues: List<List<TextLayoutResult>>,
    numberOfAxes: Int,
): Float {
    val centerX = size.width / 2f
    val centerY = size.height / 2f
    val paddedRadius = min(centerX, centerY) * (1f - config.paddingFraction)
    if (!config.scaleToFit) {
        return paddedRadius
    }
    val boxes =
        fitBoxes(
            labelConfig = config.labelConfig,
            measuredLabels = measuredLabels,
            measuredValues = measuredValues,
            numberOfAxes = numberOfAxes,
            startAngleDegrees = config.startAngleDegrees,
            alignment = { _, width, height -> Offset(x = -width / 2f, y = -height / 2f) },
        )
    return radarFitRadius(
        centerX = centerX,
        centerY = centerY,
        canvasWidth = size.width,
        canvasHeight = size.height,
        labelDistanceMultiplier = config.labelConfig.labelDistanceMultiplier,
        boxes = boxes,
        paddedRadius = paddedRadius,
    )
}

/**
 * The radius the multiple radar draws at, with its side-aligned labels: the same fit as
 * [radarChartFitRadius], using [radarLabelBoxAlignment] so the solver sees the boxes exactly where
 * the label pass will put them.
 */
internal fun DrawScope.multipleRadarFitRadius(
    config: MultipleRadarChartConfig,
    measuredLabels: List<TextLayoutResult>,
    measuredValues: List<List<TextLayoutResult>>,
    numberOfAxes: Int,
): Float {
    val centerX = size.width / 2f
    val centerY = size.height / 2f
    val paddedRadius = min(centerX, centerY) * (1f - config.radarConfig.paddingFraction)
    if (!config.radarConfig.scaleToFit) {
        return paddedRadius
    }
    val boxes =
        fitBoxes(
            labelConfig = config.radarConfig.labelConfig,
            measuredLabels = measuredLabels,
            measuredValues = measuredValues,
            numberOfAxes = numberOfAxes,
            startAngleDegrees = config.radarConfig.startAngleDegrees,
            alignment = { angle, width, height ->
                radarLabelBoxAlignment(angle = angle, textWidth = width, textHeight = height)
            },
        )
    return radarFitRadius(
        centerX = centerX,
        centerY = centerY,
        canvasWidth = size.width,
        canvasHeight = size.height,
        labelDistanceMultiplier = config.radarConfig.labelConfig.labelDistanceMultiplier,
        boxes = boxes,
        paddedRadius = paddedRadius,
    )
}

/**
 * One box per axis that draws anything at the label ring: the label itself, widened when a value
 * beneath it is wider, and extended downward by the value stack; or the stack alone when labels are
 * hidden. Axes that draw nothing there contribute no box and so no constraint.
 */
private fun fitBoxes(
    labelConfig: RadarLabelConfig,
    measuredLabels: List<TextLayoutResult>,
    measuredValues: List<List<TextLayoutResult>>,
    numberOfAxes: Int,
    startAngleDegrees: Float,
    alignment: (angle: Float, width: Float, height: Float) -> Offset,
): List<RadarFitBox> {
    val valuesBelow =
        labelConfig.showValues && labelConfig.valuePlacement == RadarValuePlacement.BELOW_AXIS_LABEL
    val boxes = mutableListOf<RadarFitBox>()
    for (index in 0 until numberOfAxes) {
        val angle =
            radarAxisAngleRadians(
                startAngleDegrees = startAngleDegrees,
                axisIndex = index,
                numberOfAxes = numberOfAxes,
            )
        val valuesForAxis =
            if (valuesBelow) {
                measuredValues.mapNotNull { datasetValues -> datasetValues.getOrNull(index) }
            } else {
                emptyList()
            }
        var stackHeight = 0f
        var stackWidth = 0f
        valuesForAxis.fastForEachIndexed { _, value ->
            stackHeight += value.size.height * (1f + labelConfig.valueGapFraction)
            stackWidth = maxOf(stackWidth, value.size.width.toFloat())
        }
        val label =
            if (labelConfig.showLabels) {
                measuredLabels.getOrNull(index)
            } else {
                null
            }
        if (label != null) {
            val labelWidth = label.size.width.toFloat()
            val labelHeight = label.size.height.toFloat()
            val boxWidth = maxOf(labelWidth, stackWidth)
            val labelOffset = alignment(angle, labelWidth, labelHeight)
            boxes.add(
                RadarFitBox(
                    angleRadians = angle,
                    offsetX = labelOffset.x + (labelWidth - boxWidth) / 2f,
                    offsetY = labelOffset.y,
                    width = boxWidth,
                    height = labelHeight + stackHeight,
                ),
            )
        } else if (valuesForAxis.isNotEmpty()) {
            val leadingGap = valuesForAxis.first().size.height * labelConfig.valueGapFraction
            boxes.add(
                RadarFitBox(
                    angleRadians = angle,
                    offsetX = -stackWidth / 2f,
                    offsetY = -(stackHeight + leadingGap) / 2f,
                    width = stackWidth,
                    height = stackHeight + leadingGap,
                ),
            )
        }
    }
    return boxes
}
