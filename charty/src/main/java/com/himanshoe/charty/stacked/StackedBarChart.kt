package com.himanshoe.charty.stacked

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.common.ChartSurface
import com.himanshoe.charty.common.ComposeList
import com.himanshoe.charty.common.config.AxisConfig
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.common.toComposeList
import com.himanshoe.charty.common.ui.drawGridLines
import com.himanshoe.charty.common.ui.drawXAxis
import com.himanshoe.charty.common.ui.drawYAxis
import com.himanshoe.charty.stacked.config.StackBarData

@Composable
fun StackedBarChart(
    stackBarData: ComposeList<StackBarData>,
    modifier: Modifier = Modifier,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    padding: Dp = 16.dp,
    spacing: Dp = 4.dp
) {

    val allDataPoints = stackBarData.data.map {
        it.dataPoints.sum()
    }.toMutableList().apply {
        add(0F)
    }

    val maxSum = stackBarData.data.maxOfOrNull {
        it.dataPoints.sum()
    } ?: 0f

    var chartWidth by remember { mutableStateOf(0f) }
    var chartHeight by remember { mutableStateOf(0f) }

    ChartSurface(
        modifier = modifier,
        padding = padding,
        axisConfig = axisConfig,
        chartData = allDataPoints.toComposeList()
    ) {
        Canvas(modifier = Modifier
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
            }) {
            val width = chartWidth - (padding).toPx()
            val height = chartHeight - (padding).toPx()
            val scaleFactor = height / maxSum

            val barCount = stackBarData.data.size
            val totalSpacing = (barCount - 1) * spacing.toPx()
            val barWidth = (width - totalSpacing) / barCount.toFloat()

            stackBarData.data.forEachIndexed { index, stackValues ->
                val x = (index * (barWidth + spacing.toPx()))
                var startY = height + padding.toPx()

                stackValues.dataPoints.forEachIndexed { stackIndex, value ->
                    val stackHeight = value * scaleFactor
                    val endY = startY - stackHeight

                    val clampedEndY = startY.coerceAtMost(endY)
                    val clampedStackHeight = startY - clampedEndY

                    drawRect(
                        color = stackValues.colors.getOrElse(stackIndex) { Color.Gray },
                        topLeft = Offset(x, clampedEndY),
                        size = Size(barWidth, clampedStackHeight)
                    )

                    startY = endY
                }
            }
        }
    }
}




