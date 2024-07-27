/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.point

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.common.ChartSurface
import com.himanshoe.charty.common.config.AxisConfig
import com.himanshoe.charty.common.config.ChartColors
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.common.math.chartDataToOffset
import com.himanshoe.charty.common.maxYValue
import com.himanshoe.charty.common.minYValue
import com.himanshoe.charty.common.ui.drawGridLines
import com.himanshoe.charty.common.ui.drawXAxis
import com.himanshoe.charty.common.ui.drawXAxisLabels
import com.himanshoe.charty.common.ui.drawYAxis
import com.himanshoe.charty.point.config.PointType

/**
 * Displays a point chart using the provided data.
 *
 * @param dataCollection The collection of chart data.
 * @param contentColor The color of the chart points.
 * @param modifier The modifier for the chart.
 * @param backgroundColor The background color of the chart.
 * @param padding The padding around the chart.
 * @param pointType The type of point to use in the chart.
 * @param axisConfig The configuration for the chart axes.
 * @param radiusScale The scale factor for the point radius.
 */
@Composable
fun PointChart(
    dataCollection: ChartDataCollection,
    contentColor: Color,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    padding: Dp = 16.dp,
    pointType: PointType = PointType.Stroke(),
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    radiusScale: Float = 0.02f,
) {
    PointChart(
        dataCollection = dataCollection,
        modifier = modifier,
        padding = padding,
        pointType = pointType,
        axisConfig = axisConfig,
        radiusScale = radiusScale,
        chartColors = ChartColors(
            contentColor = listOf(contentColor, contentColor),
            backgroundColors = listOf(backgroundColor, backgroundColor)
        )
    )
}

/**
 * Displays a point chart using the provided data.
 *
 * @param dataCollection The collection of chart data.
 * @param modifier The modifier for the chart.
 * @param padding The padding around the chart.
 * @param pointType The type of point to use in the chart.
 * @param axisConfig The configuration for the chart axes.
 * @param radiusScale The scale factor for the point radius.
 * @param chartColors The colors used in the chart.
 */
@Composable
fun PointChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    pointType: PointType = PointType.Stroke(),
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    radiusScale: Float = 0.02f,
    chartColors: ChartColors = ChartDefaults.colorDefaults(),
) {
    val points = dataCollection.data

    var chartWidth by remember { mutableStateOf(0F) }
    var chartHeight by remember { mutableStateOf(0F) }
    var pointBound by remember { mutableStateOf(0F) }

    val horizontalScale = chartWidth.div(points.count())
    val verticalScale = chartHeight.div((dataCollection.maxYValue() - dataCollection.minYValue()))

    ChartSurface(
        padding = padding,
        chartData = dataCollection,
        modifier = modifier,
        axisConfig = axisConfig
    ) {
        val contentColor = Brush.linearGradient(chartColors.contentColor)
        val backgroundColor = Brush.linearGradient(chartColors.backgroundColors)

        val minYValue = dataCollection.minYValue()
        val textMeasurer = rememberTextMeasurer()

        Canvas(
            modifier = Modifier
                .background(backgroundColor)
                .fillMaxSize()
                .onSizeChanged { size ->
                    chartWidth = size.width.toFloat()
                    chartHeight = size.height.toFloat()
                    pointBound = size.width.div(
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
            val radius = chartWidth * radiusScale

            points.fastForEachIndexed { index, point ->
                val centerOffset = chartDataToOffset(
                    index,
                    pointBound,
                    size,
                    point.yValue,
                    horizontalScale,
                )

                val x = centerOffset.x
                val y = chartHeight - ((point.yValue - minYValue) * verticalScale)
                val clampedX = x.coerceIn(centerOffset.x - radius / 2, centerOffset.x + radius / 2)
                val clampedY = y.coerceIn(radius, chartHeight - radius)

                val style = when (pointType) {
                    is PointType.Stroke -> Stroke(width = pointType.strokeWidth)
                    else -> Fill
                }
                drawCircle(
                    center = Offset(clampedX, clampedY),
                    style = style,
                    radius = radius,
                    brush = contentColor
                )

                if (points.count() < 14) {
                    drawXAxisLabels(
                        data = point.xValue,
                        center = centerOffset,
                        count = points.count(),
                        padding = padding.toPx(),
                        textMeasurer = textMeasurer,
                        minLabelCount = axisConfig.minLabelCount,
                    )
                }
            }
        }
    }
}
