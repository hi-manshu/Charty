/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.bar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.bar.config.BarChartColors
import com.himanshoe.charty.bar.config.BarChartDefaults
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.common.ChartSurface
import com.himanshoe.charty.common.config.AxisConfig
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.common.maxYValue
import com.himanshoe.charty.common.minYValue
import com.himanshoe.charty.common.ui.drawGridLines
import com.himanshoe.charty.common.ui.drawXAxis
import com.himanshoe.charty.common.ui.drawXAxisLabels
import com.himanshoe.charty.common.ui.drawYAxis

/**
 * A composable function that displays a bar chart based on the provided data collection.
 *
 * @param dataCollection The collection of chart data representing the bars in the chart.
 * @param modifier Modifier for applying styling and positioning to the chart.
 * @param barSpacing The spacing between bars in the chart.
 * @param padding Padding around the chart content to create spacing.
 * @param barColor The color used to fill the bars in the chart.
 * @param axisConfig Configuration for the chart axes, including color and stroke width.
 * @param chartColors Configuration for the colors used in the bar chart.
 */
@Composable
fun BarChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    barSpacing: Dp = 8.dp,
    padding: Dp = 16.dp,
    barColor: Color = Color.Blue,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    chartColors: BarChartColors = BarChartDefaults.defaultColor(),
) {
    val points = dataCollection.data

    val backgroundColor = Brush.linearGradient(chartColors.backgroundColors)
    val screenWidth = with(LocalContext.current) { resources.displayMetrics.widthPixels.toFloat() }
    var chartBound by remember { mutableStateOf(0F) }
    var chartWidth by remember { mutableStateOf(0F) }
    var chartHeight by remember { mutableStateOf(0F) }

    ChartSurface(
        padding = padding,
        chartData = dataCollection,
        modifier = modifier,
        axisConfig = axisConfig
    ) {
        val data = dataCollection.data

        Canvas(
            modifier = Modifier
                .background(backgroundColor)
                .fillMaxSize()
                .onSizeChanged { size ->
                    chartWidth = size.width.toFloat()
                    chartHeight = size.height.toFloat()
                    chartBound = chartWidth.div(
                        points
                            .count()
                            .times(1.2F)
                    )
                }
                .drawBehind {
                    if (axisConfig.showAxes) {
                        drawYAxis(axisConfig.axisColor, axisConfig.axisStroke)
                        drawXAxis(axisConfig.axisColor, axisConfig.axisStroke)
                    }
                    if (axisConfig.showGridLines) {
                        drawGridLines(chartWidth, chartHeight, padding.toPx())
                    }
                }
        ) {
            val maxValue = dataCollection.maxYValue()
            val barCount = data.size
            val minValue = dataCollection.minYValue()
            val range = maxValue - minValue
            val availableWidth = chartWidth - (barSpacing.toPx() * (barCount - 1))
            val maxBarWidth = availableWidth / barCount

            data.fastForEachIndexed { index, barData ->
                val barWidth = maxBarWidth.coerceAtMost(screenWidth / barCount)

                val barHeight =
                    (barData.yValue - minValue) / range * (chartHeight - axisConfig.axisStroke)

                val startX = index * (barWidth + barSpacing.toPx())
                val startY = chartHeight - barHeight

                if (barData is BarData) {
                    drawRect(
                        color = barColor,
                        topLeft = Offset(startX, startY),
                        size = Size(barWidth, barHeight)
                    )

                    if (data.count() < 14) {
                        drawXAxisLabels(
                            data = barData.xValue,
                            center = Offset(startX + barWidth / 2, startY),
                            count = data.count(),
                            padding = padding.toPx(),
                            minLabelCount = axisConfig.minLabelCount,
                        )
                    }
                } else {
                    throw NoSuchFieldException("$barData should have color")
                }
            }
        }
    }
}

/**
 * A composable function that displays a bar chart based on the provided data collection.
 *
 * @param dataCollection The collection of chart data representing the bars in the chart.
 * @param modifier Modifier for applying styling and positioning to the chart.
 * @param barSpacing The spacing between bars in the chart.
 * @param padding Padding around the chart content to create spacing.
 * @param axisConfig Configuration for the chart axes, including color and stroke width.
 * @param chartColors Configuration for the colors used in the bar chart.
 */
@Composable
fun BarChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    barSpacing: Dp = 8.dp,
    padding: Dp = 16.dp,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    chartColors: BarChartColors = BarChartDefaults.defaultColor(),
) {
    val points = dataCollection.data

    val backgroundColor = Brush.linearGradient(chartColors.backgroundColors)
    val screenWidth = with(LocalContext.current) { resources.displayMetrics.widthPixels.toFloat() }
    var chartBound by remember { mutableStateOf(0F) }
    var chartWidth by remember { mutableStateOf(0F) }
    var chartHeight by remember { mutableStateOf(0F) }

    ChartSurface(
        padding = padding,
        chartData = dataCollection,
        modifier = modifier,
        axisConfig = axisConfig
    ) {
        val data = dataCollection.data

        Canvas(
            modifier = Modifier
                .background(backgroundColor)
                .fillMaxSize()
                .onSizeChanged { size ->
                    chartWidth = size.width.toFloat()
                    chartHeight = size.height.toFloat()
                    chartBound = chartWidth.div(
                        points
                            .count()
                            .times(1.2F)
                    )
                }
                .drawBehind {
                    if (axisConfig.showAxes) {
                        drawYAxis(axisConfig.axisColor, axisConfig.axisStroke)
                        drawXAxis(axisConfig.axisColor, axisConfig.axisStroke)
                    }
                    if (axisConfig.showGridLines) {
                        drawGridLines(chartWidth, chartHeight, padding.toPx())
                    }
                }
        ) {
            val maxValue = dataCollection.maxYValue()
            val barCount = data.size
            val minValue = if (dataCollection.minYValue() < 0) {
                dataCollection.minYValue()
            } else {
                0F
            }
            val range = maxValue - minValue
            val availableWidth = chartWidth - (barSpacing.toPx() * (barCount - 1))
            val maxBarWidth = availableWidth / barCount

            data.fastForEachIndexed { index, barData ->
                val barWidth = maxBarWidth.coerceAtMost(screenWidth / barCount)
                val barHeight =
                    (barData.yValue - minValue) / range * (chartHeight - axisConfig.axisStroke)

                val startX = index * (barWidth + barSpacing.toPx())
                val startY = chartHeight - barHeight

                if (barData is BarData) {
                    if (barData.color != null) {
                        drawRect(
                            color = barData.color,
                            topLeft = Offset(startX, startY),
                            size = Size(barWidth, barHeight)
                        )
                    } else {
                        throw NoSuchFieldException("$barData should have color")
                    }

                    if (data.count() < 14) {
                        drawXAxisLabels(
                            data = barData.xValue,
                            center = Offset(startX + barWidth / 2, startY),
                            count = data.count(),
                            padding = padding.toPx(),
                            minLabelCount = axisConfig.minLabelCount,
                        )
                    }
                }
            }
        }
    }
}
