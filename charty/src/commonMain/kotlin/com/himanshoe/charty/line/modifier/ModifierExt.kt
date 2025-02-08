package com.himanshoe.charty.line.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.line.config.LineChartColorConfig
import com.himanshoe.charty.line.config.LineChartConfig
import com.himanshoe.charty.line.model.LineData

/**
 * Extension function to draw axes and grid lines on a line chart.
 *
 * @receiver Modifier The modifier to which this function is applied.
 * @param data A list of LineData objects representing the data points for the chart.
 * @param colorConfig The color configuration for the chart.
 * @param chartConfig The configuration for the chart, including line widths and path effects.
 * @param textMeasurer The TextMeasurer used to measure and draw text.
 * @param labelConfig The configuration for the labels, including whether to show labels on the axes.
 * @param minValue The minimum value on the y-axis.
 * @param yRange The range of values on the y-axis.
 * @return A Modifier with the axes and grid lines drawn.
 */
internal fun Modifier.drawAxesAndGridLines(
    data: List<LineData>,
    colorConfig: LineChartColorConfig,
    chartConfig: LineChartConfig,
    textMeasurer: TextMeasurer,
    labelConfig: LabelConfig,
    minValue: Float,
    yRange: Float,
) = this.drawBehind {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val xStep = canvasWidth / (data.size - 1)
    val yStep = canvasHeight / 4

    // Draw X and Y axis
    drawLine(
        brush = Brush.linearGradient(colorConfig.axisColor.value),
        start = Offset(0f, canvasHeight),
        end = Offset(canvasWidth, canvasHeight),
        strokeWidth = chartConfig.axisLineWidth
    )
    drawLine(
        brush = Brush.linearGradient(colorConfig.axisColor.value),
        start = Offset(0f, 0f),
        end = Offset(0f, canvasHeight),
        strokeWidth = chartConfig.axisLineWidth
    )

    // Draw parallel lines to X axis
    for (i in 1..4) {
        val yOffset = canvasHeight - i * yStep
        drawLine(
            brush = Brush.linearGradient(colorConfig.gridLineColor.value),
            start = Offset(0f, yOffset),
            pathEffect = chartConfig.gridLinePathEffect,
            end = Offset(canvasWidth, yOffset),
            strokeWidth = chartConfig.gridLineWidth
        )
    }
    // Draw labels
    if (labelConfig.showXLabel) {
        data.fastForEachIndexed { index, lineData ->
            val textCharCount =
                if (lineData.xValue.toString().length >= 3) if (data.count() <= 7) 3 else 1 else 1
            val textSizeFactor = if (data.count() <= 13) 70 else 90

            val textLayoutResult = textMeasurer.measure(
                text = lineData.xValue.toString().take(textCharCount),
                style = TextStyle(fontSize = (canvasWidth / textSizeFactor).sp),
            )

            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(
                    x = index * xStep - textLayoutResult.size.width / 2,
                    y = canvasHeight + 4.dp.toPx() // Position below the X-axis
                ),
                brush = Brush.linearGradient(labelConfig.textColor.value)
            )
        }
    }

    if (labelConfig.showYLabel) {
        for (i in 0..4) {
            val value = minValue + i * yRange / 4
            val textLayoutResult = textMeasurer.measure(
                text = value.toString(),
                style = TextStyle(fontSize = (canvasWidth / 70).sp),
            )
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(
                    x = -textLayoutResult.size.width - 8f,
                    y = canvasHeight - i * yStep - textLayoutResult.size.height / 2
                ),
                brush = Brush.linearGradient(labelConfig.textColor.value)
            )
        }
    }
}
