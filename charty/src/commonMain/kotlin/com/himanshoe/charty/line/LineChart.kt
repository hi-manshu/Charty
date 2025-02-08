package com.himanshoe.charty.line

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.common.TargetConfig
import com.himanshoe.charty.common.drawTargetLineIfNeeded
import com.himanshoe.charty.line.config.LineChartColorConfig
import com.himanshoe.charty.line.config.LineChartConfig
import com.himanshoe.charty.line.model.LineData
import com.himanshoe.charty.line.modifier.drawAxesAndGridLines

/**
 * A composable function that renders a line chart.
 *
 * @param data A lambda function that returns a list of `LineData` representing the data points for the line chart.
 * @param modifier A `Modifier` for customizing the layout or drawing behavior of the chart.
 * @param smoothLineCurve A boolean indicating whether the line should be drawn with smooth curves.
 * @param showFilledArea A boolean indicating whether the area under the line should be filled.
 * @param showLineStroke A boolean indicating whether the line should be stroked.
 * @param colorConfig A `LineChartColorConfig` object for configuring the colors of the line and filled area.
 * @param labelConfig A `LabelConfig` object for configuring the labels on the chart.
 * @param chartConfig A `LineChartConfig` object for configuring the chart's appearance and behavior.
 *
 * @throws IllegalArgumentException if both `showFilledArea` and `showLineStroke` are false.
 */
@Composable
fun LineChart(
    data: () -> List<LineData>,
    modifier: Modifier = Modifier,
    smoothLineCurve: Boolean = true,
    showFilledArea: Boolean = false,
    showLineStroke: Boolean = true,
    colorConfig: LineChartColorConfig = LineChartColorConfig.default(),
    labelConfig: LabelConfig = LabelConfig.default(),
    target: Float? = null,
    targetConfig: TargetConfig = TargetConfig.default(),
    chartConfig: LineChartConfig = LineChartConfig(),
    onClick: (LineData) -> Unit = {}
) {
    require(showFilledArea || showLineStroke) {
        "Both showFilledArea and showLineStroke cannot be false at the same time"
    }
    LineChartContent(
        data = data,
        modifier = modifier,
        colorConfig = colorConfig,
        showLineStroke = showLineStroke,
        showFilledArea = showFilledArea,
        labelConfig = labelConfig,
        chartConfig = chartConfig,
        smoothLineCurve = smoothLineCurve,
        target = target,
        onClick = onClick,
        targetConfig = targetConfig,
    )
}

@Composable
private fun LineChartContent(
    data: () -> List<LineData>,
    modifier: Modifier = Modifier,
    smoothLineCurve: Boolean = true,
    showFilledArea: Boolean = false,
    showLineStroke: Boolean = true,
    colorConfig: LineChartColorConfig = LineChartColorConfig.default(),
    labelConfig: LabelConfig = LabelConfig.default(),
    chartConfig: LineChartConfig = LineChartConfig(),
    target: Float? = null,
    targetConfig: TargetConfig = TargetConfig.default(),
    onClick: (LineData) -> Unit = {}
) {
    val lineData = data()
    val textMeasurer = rememberTextMeasurer()
    val (minValue, maxValue) = remember(lineData) {
        val min = lineData.minOfOrNull { it.yValue } ?: 0f
        val max = lineData.maxOfOrNull { it.yValue } ?: 0f
        min to max
    }
    val yRange = maxValue - minValue
    val bottomPadding = if (labelConfig.showXLabel) 24.dp else 0.dp
    val leftPadding = if (labelConfig.showYLabel) 24.dp else 0.dp
    var clickedIndex by remember { mutableStateOf(-1) }
    var clickedOffset by remember { mutableStateOf(Offset.Zero) }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = bottomPadding, start = leftPadding)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val xStep = size.width / (lineData.size - 1)
                    val tappedIndex =
                        ((offset.x + xStep / 2) / xStep).toInt().coerceIn(0, lineData.size - 1)
                    val tappedData = lineData[tappedIndex]
                    clickedIndex = tappedIndex
                    clickedOffset = offset
                    onClick(tappedData)
                }
            }
            .drawAxesAndGridLines(
                data = lineData,
                colorConfig = colorConfig,
                chartConfig = chartConfig,
                textMeasurer = textMeasurer,
                labelConfig = labelConfig,
                minValue = minValue,
                yRange = yRange
            )
    ) {
        val (canvasWidth, canvasHeight) = size
        val xStep = canvasWidth / (lineData.size - 1)
        target?.let {
            drawTargetLineIfNeeded(
                canvasWidth = canvasWidth,
                targetConfig = targetConfig,
                yPoint = canvasHeight - (target - minValue) * canvasHeight / yRange
            )
        }

        drawLineCurve(
            data = { lineData },
            canvasHeight = canvasHeight,
            xStep = xStep,
            minValue = minValue,
            maxValue = maxValue,
            chartConfig = chartConfig,
            showLineStroke = showLineStroke,
            showFilledArea = showFilledArea,
            lineColor = colorConfig.lineColor,
            fillColor = colorConfig.lineFillColor,
            smoothLineCurve = smoothLineCurve
        )

        if (clickedIndex != -1) {
            val barX = when (clickedIndex) {
                0 -> 0f
                lineData.size - 1 -> size.width - xStep / 2
                else -> clickedIndex * xStep - xStep / 2
            }
            drawRoundRect(
                brush = Brush.linearGradient(colorConfig.selectionBarColor.value),
                topLeft = Offset(barX, 0f),
                size = Size(width = xStep, height = canvasHeight),
                cornerRadius = CornerRadius(15F)
            )
        }
    }
}

internal fun DrawScope.drawLineCurve(
    data: () -> List<LineData>,
    canvasHeight: Float,
    xStep: Float,
    minValue: Float,
    maxValue: Float,
    chartConfig: LineChartConfig,
    lineColor: ChartColor,
    showFilledArea: Boolean = false,
    showLineStroke: Boolean = true,
    fillColor: ChartColor,
    smoothLineCurve: Boolean = true,
) {
    val path = Path()
    val lineData = data()
    if (lineData.isNotEmpty()) {
        val yScale = canvasHeight / (maxValue - minValue)
        val firstPointY = canvasHeight - (lineData[0].yValue - minValue) * yScale
        path.moveTo(0f, firstPointY)

        val drawPathSegment: (Int) -> Unit = { i ->
            val currentX = i * xStep
            val currentY = canvasHeight - (lineData[i].yValue - minValue) * yScale
            if (smoothLineCurve) {
                val previousX = (i - 1) * xStep
                val previousY = canvasHeight - (lineData[i - 1].yValue - minValue) * yScale
                val controlPointX = (previousX + currentX) / 2
                path.cubicTo(controlPointX, previousY, controlPointX, currentY, currentX, currentY)
            } else {
                path.lineTo(currentX, currentY)
            }
        }

        for (i in 1 until lineData.size) {
            drawPathSegment(i)
        }

        if (showFilledArea) {
            path.lineTo(size.width, size.height)
            path.lineTo(0f, size.height)
            path.close()
            drawPath(
                path = path,
                brush = Brush.linearGradient(
                    fillColor.value.map {
                        it.copy(alpha = 0.2f)
                    }
                )
            )
        }

        if (showLineStroke) {
            path.reset()
            path.moveTo(0f, firstPointY)
            for (i in 1 until lineData.size) {
                drawPathSegment(i)
            }
            drawPath(
                path = path,
                brush = Brush.linearGradient(lineColor.value),
                style = Stroke(cap = chartConfig.lineCap, width = chartConfig.lineChartStrokeWidth)
            )
        }

        if (chartConfig.drawPointerCircle) {
            // Draw circles with borders at each data point
            lineData.forEachIndexed { index, lineDataPoint ->
                val x = index * xStep
                val y = canvasHeight - (lineDataPoint.yValue - minValue) * yScale
                drawCircle(
                    color = lineColor.value.first(),
                    radius = 10f,
                    center = Offset(x, y)
                )
                drawCircle(
                    color = fillColor.value.first(),
                    radius = 5f,
                    center = Offset(x, y)
                )
            }
        }
    }
}
