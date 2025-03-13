package com.himanshoe.charty.line

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFlatMap
import androidx.compose.ui.util.fastForEach
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.common.TargetConfig
import com.himanshoe.charty.common.drawTargetLineIfNeeded
import com.himanshoe.charty.line.config.LineChartConfig
import com.himanshoe.charty.line.ext.drawAxesAndGridLines
import com.himanshoe.charty.line.ext.drawIndicatorLine
import com.himanshoe.charty.line.ext.drawLineCurve
import com.himanshoe.charty.line.ext.drawMultiLineChartTooltip
import com.himanshoe.charty.line.ext.drawValueOnLine
import com.himanshoe.charty.line.model.LineData
import com.himanshoe.charty.line.model.MultiLineData

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
    chartConfig: LineChartConfig = LineChartConfig(),
    onValueChange: (List<LineData>) -> Unit = {}
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
        onValueChange = onValueChange,
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
    onValueChange: (List<LineData>) -> Unit = {},
    targetConfig: TargetConfig = TargetConfig.default(),
    chartConfig: LineChartConfig = LineChartConfig(),
) {
    val multiLineData = data()
    val textMeasurer = rememberTextMeasurer()
    val (minValue, maxValue) = remember(multiLineData) {
        val min = multiLineData.fastFlatMap { it.data }.minOfOrNull { it.yValue } ?: 0f
        val max = multiLineData.fastFlatMap { it.data }.maxOfOrNull { it.yValue } ?: 0f
        min to max
    }
    val dragPosition = remember { mutableStateOf<Offset?>(null) }
    val yRange = maxValue - minValue
    val bottomPadding = if (labelConfig.showXLabel) 24.dp else 0.dp
    val leftPadding = if (labelConfig.showYLabel) 24.dp else 0.dp
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = bottomPadding, start = leftPadding)
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { change, _ ->
                        dragPosition.value = change.position
                    },
                    onDragEnd = {
                        dragPosition.value = null
                    }
                )
            }
            .drawAxesAndGridLines(
                data = multiLineData.fastFlatMap { it.data },
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

        target?.let {
            val yPoint =
                canvasHeight - (target.minus(minValue)).times(canvasHeight / yRange)

            drawTargetLineIfNeeded(
                canvasWidth = canvasWidth,
                targetConfig = targetConfig,
                yPoint = yPoint
            )
        }

        multiLineData.fastForEach { lineData ->
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
                smoothLineCurve = smoothLineCurve,
            )
            if (chartConfig.lineConfig.showValueOnLine) {
                drawValueOnLine(
                    lineData = lineData.data,
                    xStep = xStep,
                    minValue = minValue,
                    yRange = yRange,
                    canvasHeight = canvasHeight,
                    textMeasurer = textMeasurer,
                    valueTextColor = chartConfig.lineConfig.valueTextColor,
                    valueTextStyle = chartConfig.lineConfig.valueTextStyle
                )
            }
        }

        if (chartConfig.interactionTooltipConfig.isLongPressDragEnabled) {
            dragPosition.value?.let { position ->
                val xTooltipPosition = when {
                    position.x < leftPadding.toPx() -> leftPadding.toPx()
                    position.x > size.width -> size.width
                    else -> position.x
                }
                drawIndicatorLine(
                    xTooltipPosition = xTooltipPosition,
                    canvasHeight = canvasHeight,
                    interactionTooltipConfig = chartConfig.interactionTooltipConfig
                )
                drawMultiLineChartTooltip(
                    multiLineData = multiLineData,
                    xTooltipPosition = xTooltipPosition,
                    xScaleFactor = xStep,
                    minValue = minValue,
                    yRange = yRange,
                    canvasHeight = canvasHeight,
                    textMeasurer = textMeasurer,
                    onValueChange = onValueChange,
                    interactionTooltipConfig = chartConfig.interactionTooltipConfig
                )
            }
        }
    }
}
