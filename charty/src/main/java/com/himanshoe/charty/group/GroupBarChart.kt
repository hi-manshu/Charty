/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.group

import android.graphics.Paint
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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.common.ChartSurface
import com.himanshoe.charty.common.ComposeList
import com.himanshoe.charty.common.config.AxisConfig
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.common.config.ChartyLabelTextConfig
import com.himanshoe.charty.common.toComposeList
import com.himanshoe.charty.common.ui.drawGridLines
import com.himanshoe.charty.common.ui.drawXAxis
import com.himanshoe.charty.common.ui.drawYAxis
import com.himanshoe.charty.group.config.GroupBarChartColors
import com.himanshoe.charty.group.config.GroupBarChartDefaults
import com.himanshoe.charty.group.model.GroupBarData

/**
 * Composable function that renders a grouped bar chart.
 *
 * @param groupBarDataCollection The collection of group bar chart data.
 * @param modifier The modifier for the chart.
 * @param padding The padding around the chart. Defaults to 16.dp.
 * @param barWidthRatio The ratio of the bar width to the available space. Defaults to 0.8f.
 * @param axisConfig The configuration for the chart's axis. Defaults to [ChartDefaults.axisConfigDefaults].
 * @param textLabelTextConfig The configuration for the text labels in the chart. Defaults to [ChartDefaults.defaultTextLabelConfig].
 * @param chartColors Configuration for the colors used in the group bars chart.
 */
@Composable
fun GroupedBarChart(
    groupBarDataCollection: ComposeList<GroupBarData>,
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    barWidthRatio: Float = 0.8f,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    textLabelTextConfig: ChartyLabelTextConfig = ChartDefaults.defaultTextLabelConfig(),
    chartColors: GroupBarChartColors = GroupBarChartDefaults.defaultColor(),
) {
    val backgroundColor = Brush.linearGradient(chartColors.backgroundColors)

    require(barWidthRatio in 0.4f..0.9f) { "barWidthRatio must be within the range of 0.4F to 0.9F, but use 0.8F for best looking View" }

    val allDataPoints = groupBarDataCollection.data.flatMap { it.dataPoints }
    val maxValue = allDataPoints.maxOrNull() ?: 0f
    val minValue = allDataPoints.minOrNull() ?: 0f
    val newItems = if (allDataPoints.min() > 0F) {
        listOf(0F) + allDataPoints
    } else {
        allDataPoints
    }
    var chartWidth by remember { mutableStateOf(0F) }
    var chartHeight by remember { mutableStateOf(0F) }

    ChartSurface(
        modifier = modifier,
        padding = padding,
        axisConfig = axisConfig,
        chartData = newItems.toComposeList(),
    ) {
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
            val groupCount = groupBarDataCollection.data.size
            val barCount = groupBarDataCollection.data.firstOrNull()?.dataPoints?.size ?: 0

            val groupWidth = chartWidth / groupCount
            val totalBarWidth = groupWidth * barWidthRatio
            val barWidth = totalBarWidth / barCount.toFloat()

            groupBarDataCollection.data.fastForEachIndexed { groupIndex, groupedBarData ->
                if (axisConfig.showGridLabel) {
                    drawLabel(
                        groupedBarData = groupedBarData,
                        groupIndex = groupIndex,
                        groupWidth = groupWidth,
                        chartHeight = chartHeight,
                        textLabelTextConfig = textLabelTextConfig
                    )
                }

                groupedBarData.dataPoints.fastForEachIndexed { barIndex, barValue ->
                    val x = calculateBarX(groupIndex, groupWidth, barWidthRatio, barIndex, barWidth)
                    val barHeight = calculateBarHeight(barValue, minValue, maxValue, chartHeight)

                    drawGroupedBar(
                        x = x,
                        barHeight = barHeight,
                        width = barWidth,
                        height = chartHeight,
                        colors = groupedBarData.colors,
                        barIndex = barIndex
                    )
                }
            }
        }
    }
}

private fun DrawScope.drawLabel(
    groupedBarData: GroupBarData,
    groupIndex: Int,
    groupWidth: Float,
    chartHeight: Float,
    textLabelTextConfig: ChartyLabelTextConfig
) {
    val label = groupedBarData.label
    val labelX = (groupIndex * groupWidth) + (groupWidth / 2f)
    val labelY = chartHeight + size.width / 20

    drawIntoCanvas {
        it.nativeCanvas.drawText(
            label,
            labelX,
            labelY,
            Paint().apply {
                color = textLabelTextConfig.textColor.toArgb()
                textAlign = Paint.Align.CENTER
                textSize = size.width / 35
            }
        )
    }
}

private fun calculateBarX(
    groupIndex: Int,
    groupWidth: Float,
    barWidthRatio: Float,
    barIndex: Int,
    barWidth: Float
) = (groupIndex * groupWidth) +
    ((1 - barWidthRatio) / 2) *
    groupWidth +
    (barIndex * barWidth) + (barWidth / 2f)

private fun calculateBarHeight(
    barValue: Float,
    minValue: Float,
    maxValue: Float,
    chartHeight: Float
): Float {
    val newMinValue = if (minValue < 0) minValue else 0F
    val range = maxValue - newMinValue
    val normalizedValue = barValue - newMinValue
    val heightRatio = if (range != 0f) normalizedValue / range else 0f
    return heightRatio * chartHeight
}

private fun DrawScope.drawGroupedBar(
    x: Float,
    barHeight: Float,
    width: Float,
    height: Float,
    colors: List<Color>,
    barIndex: Int
) {
    val barRect = Rect(
        left = x - (width / 2f),
        top = height - barHeight,
        right = x + (width / 2f),
        bottom = height
    )

    drawRoundRect(
        color = colors.getOrElse(barIndex % colors.size) { Color.Gray },
        topLeft = Offset(barRect.left, barRect.top),
        size = Size(barRect.width, barRect.height),
    )
}
