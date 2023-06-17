package com.himanshoe.charty.group

import android.graphics.Paint
import androidx.compose.foundation.Canvas
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
import com.himanshoe.charty.common.ui.drawGridLines
import com.himanshoe.charty.common.ui.drawXAxis
import com.himanshoe.charty.common.ui.drawYAxis
import com.himanshoe.charty.group.model.GroupBarData

@Composable
fun GroupedBarChart(
    groupBarDataCollection: ComposeList<GroupBarData>,
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    barWidthRatio: Float = 0.8f,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    textLabelTextConfig: ChartyLabelTextConfig = ChartDefaults.defaultTextLabelConfig(),
) {
    require(barWidthRatio in 0.4f..0.9f) { "barWidthRatio must be within the range of 0.4F to 0.9F, but use 0.8F for best looking View" }

    val allDataPoints = groupBarDataCollection.data.flatMap { it.dataPoints }
    val maxValue = allDataPoints.maxOrNull() ?: 0f
    val minValue = allDataPoints.minOrNull() ?: 0f

    var chartWidth by remember { mutableStateOf(0F) }
    var chartHeight by remember { mutableStateOf(0F) }

    ChartSurface(
        modifier = modifier,
        padding = padding,
        axisConfig = axisConfig,
        chartData = ComposeList(allDataPoints),
    ) {
        Canvas(
            modifier = Modifier
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
    val range = maxValue - minValue
    val normalizedValue = barValue - minValue
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
