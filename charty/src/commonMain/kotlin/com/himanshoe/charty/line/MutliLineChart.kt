package com.himanshoe.charty.line

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.bar.config.TargetConfig
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.line.config.LineChartConfig
import com.himanshoe.charty.line.model.MultiLineData
import com.himanshoe.charty.line.modifier.drawAxesAndGridLines

/**
 * A composable function that renders a multi-line chart.
 *
 * @param data A lambda function that returns a list of `MultiLineData` representing the data points and color configuration for each line.
 * @param modifier A `Modifier` for customizing the layout or drawing behavior of the chart.
 * @param smoothLineCurve A boolean indicating whether the lines should be drawn with smooth curves.
 * @param showFilledArea A boolean indicating whether the area under the lines should be filled.
 * @param showLineStroke A boolean indicating whether the lines should be stroked.
 * @param labelConfig A `LabelConfig` object for configuring the labels on the chart.
 * @param chartConfig A `LineChartConfig` object for configuring the chart's appearance and behavior.
 *
 * @throws IllegalArgumentException if both `showFilledArea` and `showLineStroke` are false.
 */
@Composable
fun MultiLineChart(
    data: () -> List<MultiLineData>,
    modifier: Modifier = Modifier,
    smoothLineCurve: Boolean = true,
    showFilledArea: Boolean = false,
    target: Float? = null,
    targetConfig: TargetConfig = TargetConfig.default(),
    showLineStroke: Boolean = true,
    labelConfig: LabelConfig = LabelConfig.default(),
    chartConfig: LineChartConfig = LineChartConfig()
) {
    require(showFilledArea || showLineStroke) {
        "Both showFilledArea and showLineStroke cannot be false at the same time"
    }
    MultiLineChartContent(
        data = data,
        modifier = modifier,
        labelConfig = labelConfig,
        targetConfig = targetConfig,
        target = target,
        showLineStroke = showLineStroke,
        showFilledArea = showFilledArea,
        chartConfig = chartConfig,
        smoothLineCurve = smoothLineCurve
    )
}

@Composable
private fun MultiLineChartContent(
    data: () -> List<MultiLineData>,
    modifier: Modifier = Modifier,
    smoothLineCurve: Boolean = true,
    showFilledArea: Boolean = true,
    showLineStroke: Boolean = true,
    labelConfig: LabelConfig = LabelConfig.default(),
    target: Float? = null,
    targetConfig: TargetConfig = TargetConfig.default(),
    chartConfig: LineChartConfig = LineChartConfig(),
) {
    val multiLineData = data()
    val textMeasurer = rememberTextMeasurer()
    val (minValue, maxValue) = remember(multiLineData) {
        val min = multiLineData.flatMap { it.data }.minOfOrNull { it.yValue } ?: 0f
        val max = multiLineData.flatMap { it.data }.maxOfOrNull { it.yValue } ?: 0f
        min to max
    }
    val yRange = maxValue - minValue
    val bottomPadding = if (labelConfig.showXLabel) 24.dp else 0.dp
    val leftPadding = if (labelConfig.showYLabel) 24.dp else 0.dp
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = bottomPadding, start = leftPadding)
            .drawAxesAndGridLines(
                data = multiLineData.flatMap { it.data },
                colorConfig = multiLineData.first().colorConfig,
                chartConfig = chartConfig,
                textMeasurer = textMeasurer,
                labelConfig = labelConfig,
                minValue = minValue,
                yRange = yRange
            )
    ) {
        val (canvasWidth, canvasHeight) = size
        val xStep = canvasWidth / (multiLineData.first().data.size - 1)

        multiLineData.forEach { lineData ->
            drawLineCurve(
                data = { lineData.data },
                canvasHeight = canvasHeight,
                xStep = xStep,
                minValue = minValue,
                maxValue = maxValue,
                showLineStroke = showLineStroke,
                showFilledArea = showFilledArea,
                chartConfig = chartConfig,
                lineColor = lineData.colorConfig.lineColor,
                fillColor = lineData.colorConfig.lineFillColor,
                smoothLineCurve = smoothLineCurve
            )
        }
        target?.let {
            val targetY = canvasHeight - (it - minValue) * (canvasHeight / yRange)
            drawLine(
                brush = Brush.linearGradient(targetConfig.targetLineBarColors.value),
                start = Offset(0f, targetY),
                end = Offset(canvasWidth, targetY),
                strokeWidth = targetConfig.targetStrokeWidth
            )
        }
    }
}