package com.himanshoe.charty.bar.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.common.getTetStyle

/**
 * Extension function to draw axes and grid lines on a canvas.
 *
 * @param maxValue The maximum value on the y-axis.
 * @param step The step value for the grid lines.
 * @param textMeasurer The text measurer for drawing text.
 * @param labelConfig The configuration for the labels.
 * @param showAxisLines A boolean indicating whether to show axis lines.
 * @param showGridLines A boolean indicating whether to show grid lines.
 * @return A Modifier with the axes and grid lines drawn.
 */
internal fun Modifier.drawAxesAndGridLines(
    maxValue: Float,
    step: Float,
    textMeasurer: TextMeasurer,
    labelConfig: LabelConfig,
    showAxisLines: Boolean,
    showGridLines: Boolean
) = this.drawWithCache {
    val canvasWidth = size.width
    val canvasHeight = size.height
    onDrawBehind {
        if (showAxisLines) {
            // Draw axes
            drawLine(
                color = Color.Black,
                start = Offset(0f, canvasHeight),
                end = Offset(canvasWidth, canvasHeight),
                strokeWidth = 2f
            )
            drawLine(
                color = Color.Black,
                start = Offset(0f, 0f),
                end = Offset(0f, canvasHeight),
                strokeWidth = 2f
            )
        }
        if (showGridLines) {
            // Draw grid lines
            for (i in 0..4) {
                val yValue = step * i
                val yOffset = canvasHeight - (yValue / maxValue) * canvasHeight
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, yOffset),
                    end = Offset(canvasWidth, yOffset),
                    strokeWidth = 1f
                )
            }
        }

        // Draw y-axis labels
        if (labelConfig.showYLabel) {
            for (i in 0..4) {
                val yValue = step * i
                val yOffset = canvasHeight - (yValue / maxValue) * canvasHeight
                val textLayoutResult = textMeasurer.measure(
                    text = yValue.toString(),
                    style = labelConfig.getTetStyle(fontSize = 12.sp)
                )
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x = (-textLayoutResult.size.width - 4).toFloat(),
                        y = yOffset - textLayoutResult.size.height / 2 + 4
                    ),
                )
            }
        }
    }
}

/**
 * Extension function to draw axis lines for a vertical chart.
 *
 * @param hasNegativeValues A boolean indicating whether the data contains negative values.
 * @param axisLineColor The color of the axis lines.
 * @return A Modifier with the axis lines drawn.
 */
internal fun Modifier.drawAxisLineForVerticalChart(
    hasNegativeValues: Boolean = true,
    axisLineColor: ChartColor,
): Modifier =
    this.drawWithCache {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val yAxis =
            if (hasNegativeValues) {
                canvasHeight / 2
            } else {
                canvasHeight
            }
        onDrawBehind {
            // Draw the x-axis
            drawLine(
                brush = Brush.linearGradient(axisLineColor.value),
                start = Offset(0f, yAxis),
                end = Offset(canvasWidth, yAxis),
                strokeWidth = 2f,
            )

            // Draw the y-axis
            drawLine(
                brush = Brush.linearGradient(axisLineColor.value),
                start = Offset(0f, 0f),
                end = Offset(0f, canvasHeight),
                strokeWidth = 2f,
            )
        }
    }

/**
 * Extension function to draw y-axis labels on a canvas.
 *
 * @param minValue The minimum value on the y-axis.
 * @param step The step value for the labels.
 * @param maxValue The maximum value on the y-axis.
 * @param textMeasurer The text measurer for drawing text.
 * @param count The number of labels to draw.
 * @return A Modifier with the y-axis labels drawn.
 */
internal fun Modifier.drawYAxisLabel(
    minValue: Float,
    step: Float,
    maxValue: Float,
    textMeasurer: TextMeasurer,
    count: Int,
    labelConfig: LabelConfig,
): Modifier =
    this.drawWithCache {
        onDrawBehind {
            repeat(5) { i ->
                val value = minValue + i * step
                val displayValue = value.toString().take(4)
                val y = size.height - ((value - minValue) / (maxValue - minValue)) * size.height
                val textLayoutResult = textMeasurer.measure(
                    text = displayValue,
                    style = labelConfig.getTetStyle(fontSize = (size.width / count / 10).sp),
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                )
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        -textLayoutResult.size.width - 8f,
                        y - textLayoutResult.size.height / 2
                    ),
                )
            }
        }
    }

/**
 * Extension function to draw range lines on a canvas for a vertical chart.
 *
 * @param hasNegativeValues A boolean indicating whether the data contains negative values.
 * @param rangeLineColor The color of the range lines.
 * @return A Modifier with the range lines drawn.
 */
internal fun Modifier.drawRangeLinesForVerticalChart(
    hasNegativeValues: Boolean = true,
    rangeLineColor: ChartColor,
): Modifier =
    this.drawWithCache {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val yAxis =
            if (hasNegativeValues) {
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
                        brush = Brush.linearGradient(rangeLineColor.value),
                        start = Offset(0f, yOffsetTop),
                        end = Offset(canvasWidth, yOffsetTop),
                        strokeWidth = 1f,
                        pathEffect = pathEffect,
                    )
                    drawLine(
                        brush = Brush.linearGradient(rangeLineColor.value),
                        start = Offset(0f, yOffsetBottom),
                        end = Offset(canvasWidth, yOffsetBottom),
                        strokeWidth = 1f,
                        pathEffect = pathEffect,
                    )
                }
            } else {
                val totalLines = 4
                val spacing = canvasHeight / (totalLines + 1)
                for (i in 1..totalLines) {
                    val yOffset = i * spacing
                    drawLine(
                        brush = Brush.linearGradient(rangeLineColor.value),
                        start = Offset(0f, yOffset),
                        end = Offset(canvasWidth, yOffset),
                        strokeWidth = 1f,
                        pathEffect = pathEffect,
                    )
                }
            }
        }
    }
