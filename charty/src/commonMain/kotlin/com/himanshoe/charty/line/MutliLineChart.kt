package com.himanshoe.charty.line

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.line.config.LineChartColorConfig
import com.himanshoe.charty.line.config.LineChartConfig
import com.himanshoe.charty.line.model.LineData
import com.himanshoe.charty.line.modifier.drawAxesAndGridLines

/**
 * A composable function that renders a multi-line chart.
 *
 * @param data A lambda function that returns a list of lists of `LineData` representing the data points for each line.
 * @param modifier A `Modifier` for customizing the layout or drawing behavior of the chart.
 * @param smoothLineCurve A boolean indicating whether the lines should be drawn with smooth curves.
 * @param showFilledArea A boolean indicating whether the area under the lines should be filled.
 * @param showLineStroke A boolean indicating whether the lines should be stroked.
 * @param colorConfig A list of `LineChartColorConfig` objects for configuring the colors of the lines and filled areas.
 * @param labelConfig A `LabelConfig` object for configuring the labels on the chart.
 * @param chartConfig A `LineChartConfig` object for configuring the chart's appearance and behavior.
 *
 * @throws IllegalArgumentException if both `showFilledArea` and `showLineStroke` are false.
 * @throws IllegalArgumentException if the size of `data` and `colorConfig` are not the same.
 */
@Composable
fun MultiLineChart(
    data: () -> List<List<LineData>>,
    modifier: Modifier = Modifier,
    smoothLineCurve: Boolean = true,
    showFilledArea: Boolean = false,
    showLineStroke: Boolean = true,
    colorConfig: List<LineChartColorConfig> = listOf(LineChartColorConfig.default()),
    labelConfig: LabelConfig = LabelConfig.default(),
    chartConfig: LineChartConfig = LineChartConfig()
) {
    require(showFilledArea || showLineStroke) {
        "Both showFilledArea and showLineStroke cannot be false at the same time"
    }
    require(data().size == colorConfig.size) {
        "Size of data and colorConfig should be same"
    }
    MultiLineChartContent(
        data = data,
        modifier = modifier,
        colorConfig = colorConfig,
        labelConfig = labelConfig,
        showLineStroke = showLineStroke,
        showFilledArea = showFilledArea,
        chartConfig = chartConfig,
        smoothLineCurve = smoothLineCurve
    )
}

@Composable
private fun MultiLineChartContent(
    data: () -> List<List<LineData>>,
    modifier: Modifier = Modifier,
    smoothLineCurve: Boolean = true,
    showFilledArea: Boolean = true,
    showLineStroke: Boolean = true,
    colorConfig: List<LineChartColorConfig> = listOf(LineChartColorConfig.default()),
    labelConfig: LabelConfig = LabelConfig.default(),
    chartConfig: LineChartConfig = LineChartConfig(),
) {
    val multiLineData = data()
    val textMeasurer = rememberTextMeasurer()
    val (minValue, maxValue) = remember(multiLineData) {
        val min = multiLineData.flatten().minOfOrNull { it.yValue } ?: 0f
        val max = multiLineData.flatten().maxOfOrNull { it.yValue } ?: 0f
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
                lineData = multiLineData.flatten(),
                colorConfig = colorConfig.first(),
                chartConfig = chartConfig,
                textMeasurer = textMeasurer,
                labelConfig = labelConfig,
                minValue = minValue,
                yRange = yRange
            )
    ) {
        val (canvasWidth, canvasHeight) = size
        val xStep = canvasWidth / (multiLineData.first().size - 1)

        multiLineData.forEachIndexed { index, lineData ->
            drawLineCurve(
                data = { lineData },
                canvasHeight = canvasHeight,
                xStep = xStep,
                minValue = minValue,
                maxValue = maxValue,
                showLineStroke = showLineStroke,
                showFilledArea = showFilledArea,
                chartConfig = chartConfig,
                lineColor = colorConfig.getOrElse(index) { LineChartColorConfig.default() }.lineColor,
                fillColor = colorConfig.getOrElse(index) { LineChartColorConfig.default() }.lineFillColor,
                smoothLineCurve = smoothLineCurve
            )
        }
    }
}