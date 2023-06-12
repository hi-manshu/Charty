package com.himanshoe.charty.bubble

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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.bubble.model.BubbleData
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.common.ChartSurface
import com.himanshoe.charty.common.config.AxisConfig
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.common.maxYValue
import com.himanshoe.charty.common.minYValue
import com.himanshoe.charty.common.ui.drawGridLines
import com.himanshoe.charty.common.ui.drawXAxis
import com.himanshoe.charty.common.ui.drawYAxis
import com.himanshoe.charty.line.config.CurvedLineChartColors
import com.himanshoe.charty.line.config.CurvedLineChartDefaults

@Composable
fun BubbleChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    chartColors: CurvedLineChartColors = CurvedLineChartDefaults.defaultColor(),
) {
    val points = dataCollection.data

    var chartWidth by remember { mutableStateOf(0f) }
    var chartHeight by remember { mutableStateOf(0f) }

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
        val data = dataCollection.data.filterIsInstance<BubbleData>()
        val minYValue = dataCollection.minYValue()

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
            val maxVolumeSize = data.maxOf { it.volumeSize }

            dataCollection.data.fastForEachIndexed { index, data ->

                if (data is BubbleData) {
                    val bubbleRadius =
                        data.volumeSize / maxVolumeSize * 50 // Adjust the scaling factor here
                    val x = index.toFloat() * horizontalScale + bubbleRadius
                    val y =
                        (chartHeight - ((data.yValue - minYValue) * verticalScale) - bubbleRadius)
                            .coerceIn(0f, chartHeight - bubbleRadius * 2) + bubbleRadius

                    drawCircle(
                        color = Color.Blue,
                        radius = bubbleRadius,
                        center = Offset(x, y)
                    )
                } else {
                    throw Exception("Use ChartDataCollection for BubbleData")
                }
            }
        }
    }
}
