/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.area

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.himanshoe.charty.area.config.AreaChartColors
import com.himanshoe.charty.area.config.AreaChartDefaults
import com.himanshoe.charty.area.model.AreaData
import com.himanshoe.charty.common.ChartSurface
import com.himanshoe.charty.common.ComposeList
import com.himanshoe.charty.common.config.AxisConfig
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.common.toComposeList
import com.himanshoe.charty.common.ui.drawGridLines
import com.himanshoe.charty.common.ui.drawXAxis
import com.himanshoe.charty.common.ui.drawYAxis

/**
 * A composable function that displays an area chart based on the provided area data.
 *
 * @param areaData The collection of area data containing labels and points that define the areas on the chart.
 * @param modifier Modifier for applying styling and positioning to the chart.
 * @param axisConfig Configuration for the chart axes, including color and stroke width.
 * @param padding Padding around the chart content to create spacing.
 * @param chartColors Configuration for the colors used in the area chart.
 */
@Composable
fun AreaChart(
    areaData: ComposeList<AreaData>,
    modifier: Modifier = Modifier,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    padding: Dp = 16.dp,
    chartColors: AreaChartColors = AreaChartDefaults.defaultColor(),
) {
    val backgroundColor = Brush.linearGradient(chartColors.backgroundColors)
    val items = areaData.data.flatMap { it.points }
    val maxValue = items.maxOrNull() ?: 0F
    val minValue = items.minOrNull() ?: 0f
    var chartWidth by remember { mutableStateOf(0F) }
    var chartHeight by remember { mutableStateOf(0F) }

    ChartSurface(
        modifier = modifier.fillMaxSize(),
        padding = padding,
        chartData = items.toComposeList(),
        content = {
            Column {
                Canvas(
                    modifier = Modifier
                        .background(backgroundColor)
                        .fillMaxSize()
                        .onSizeChanged { size ->
                            chartWidth = size.width.toFloat()
                            chartHeight = size.height.toFloat()
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
                    val width = size.width
                    val height = size.height

                    val xStep = width / (areaData.data[0].points.size - 1)
                    val yStep = height / (maxValue - minValue)

                    areaData.data.fastForEach { data ->
                        val dataPoints = data.points
                        val dataPointsPath = Path()

                        dataPointsPath.moveTo(0f, height - ((dataPoints[0] - minValue) * yStep))

                        for (i in 1 until dataPoints.size) {
                            val x = i * xStep
                            val y = height - ((dataPoints[i] - minValue) * yStep)
                            dataPointsPath.lineTo(x, y)
                        }

                        dataPointsPath.lineTo(width, height)
                        dataPointsPath.lineTo(0f, height)

                        drawPath(
                            path = dataPointsPath,
                            color = data.color,
                            style = Fill,
                        )
                    }
                }
            }
        }
    )
}
