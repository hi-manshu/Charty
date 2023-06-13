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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.himanshoe.charty.area.model.AreaData
import com.himanshoe.charty.common.ChartSurface
import com.himanshoe.charty.common.ComposeList
import com.himanshoe.charty.common.config.AxisConfig
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.common.ui.drawGridLines
import com.himanshoe.charty.common.ui.drawXAxis
import com.himanshoe.charty.common.ui.drawYAxis
import kotlin.math.roundToInt

@Composable
fun AreaChart(
    areaData: ComposeList<AreaData>,
    modifier: Modifier = Modifier,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    padding: Dp = 16.dp,
) {
    val items = areaData.data.flatMap { it.points }
    val maxValue = items.maxOrNull()?.roundToInt() ?: 0
    var chartWidth by remember { mutableStateOf(0F) }
    var chartHeight by remember { mutableStateOf(0F) }

    ChartSurface(
        modifier = modifier.fillMaxSize(),
        padding = padding,
        chartData = ComposeList(items),
        content = {
            Column {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
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
                    val yStep = height / maxValue

                    areaData.data.fastForEach { data ->
                        val dataPoints = data.points
                        val dataPointsPath = Path()

                        dataPointsPath.moveTo(0f, height - (dataPoints[0] * yStep))

                        for (i in 1 until dataPoints.size) {
                            val x = i * xStep
                            val y = height - (dataPoints[i] * yStep)
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
