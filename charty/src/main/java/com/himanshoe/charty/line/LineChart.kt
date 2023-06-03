package com.himanshoe.charty.line

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.common.ChartDataCollection
import com.himanshoe.charty.common.ChartSurface
import com.himanshoe.charty.common.config.AxisConfig
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.common.ui.drawXAxisLabels
import com.himanshoe.charty.common.ui.drawYAxisLabels
import com.himanshoe.charty.line.config.LineChartColors
import com.himanshoe.charty.line.config.LineChartDefaults
import com.himanshoe.charty.line.config.LineConfig
import com.himanshoe.charty.line.model.LineData

@Composable
fun LineChart(
    dataCollection: ChartDataCollection,
    dotColor: Color,
    lineColor: Color,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    padding: Dp = 16.dp,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    lineConfig: LineConfig = LineChartDefaults.defaultConfig(),
    radiusScale: Float = 0.02f,
) {
    LineChart(
        dataCollection = dataCollection,
        modifier = modifier,
        radiusScale = radiusScale,
        padding = padding,
        axisConfig = axisConfig,
        lineConfig = lineConfig,
        chartColors = LineChartColors(
            dotColor = listOf(dotColor, dotColor),
            lineColor = listOf(lineColor, lineColor),
            backgroundColors = listOf(backgroundColor, backgroundColor)
        )
    )
}

@Composable
fun LineChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    radiusScale: Float = 0.02f,
    lineConfig: LineConfig = LineChartDefaults.defaultConfig(),
    chartColors: LineChartColors = LineChartDefaults.defaultColor(),
) {
    ChartSurface(
        padding = padding,
        chartData = dataCollection,
        modifier = modifier,
        axisConfig = axisConfig
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val graphPathPoints = mutableListOf<PointF>()

            val xStep = size.width / (dataCollection.data.size - 1)
            val yStep =
                size.height / (dataCollection.data.maxOf { it.yValue } - dataCollection.data.minOf { it.yValue })

            dataCollection.data.forEachIndexed { index, data ->
                val x = index * xStep
                val y =
                    size.height - (data.yValue - dataCollection.data.minOf { it.yValue }) * yStep
                val innerX = x.coerceIn(x - radiusScale * size.width, x + radiusScale * size.width)
                val innerY = y.coerceIn(0f, size.height)

                graphPathPoints.add(PointF(innerX, innerY))

                if (index > 0) {
                    val prevX = (index - 1) * xStep
                    val prevY =
                        size.height - (dataCollection.data[index - 1].yValue - dataCollection.data.minOf { it.yValue }) * yStep
                    val prevInnerX = prevX.coerceIn(
                        prevX - radiusScale * size.width,
                        prevX + radiusScale * size.width
                    )
                    val prevInnerY = prevY.coerceIn(0f, size.height)

                    val path = Path().apply {
                        moveTo(prevInnerX, prevInnerY)
                        cubicTo(
                            prevInnerX, prevInnerY,
                            innerX, innerY,
                            innerX, innerY
                        )
                        lineTo(innerX, size.height)
                        lineTo(prevInnerX, size.height)
                        close()
                    }

                    drawPath(
                        path = path,
                        brush = Brush.linearGradient(
                            colors = chartColors.backgroundColors,
                        )
                    )

                    drawPath(
                        path = path,
                        brush = Brush.linearGradient(
                            colors = chartColors.lineColor,
                        ),
                        style = Stroke(width = lineConfig.strokeSize.toPx())
                    )
                }
            }

            if (lineConfig.hasDotMarker) {
                graphPathPoints.forEach { point ->
                    drawCircle(
                        color = chartColors.dotColor.first(),
                        radius = radiusScale * size.width,
                        center = Offset(point.x, point.y)
                    )
                }
            }
        }
    }
}


@Composable
fun LineChartPreview(modifier: Modifier = Modifier) {
    Column(modifier) {
        CurveLineChart(
            dataCollection = ChartDataCollection(generateMockPointList()),
            modifier = Modifier
                .size(450.dp),
        )
    }
}

private fun generateMockPointList(): List<LineData> {
    return listOf(
        LineData(0F, "Jan"),
        LineData(10F, "Feb"),
        LineData(05F, "Mar"),
        LineData(50F, "Apr"),
        LineData(03F, "June"),
        LineData(9F, "July"),
        LineData(40F, "Aug"),
        LineData(60F, "Sept"),
        LineData(33F, "Oct"),
        LineData(11F, "Nov"),
        LineData(27F, "Dec"),
        LineData(10F, "Jan"),
        LineData(13F, "Oct"),
        LineData(-10F, "Nov"),
        LineData(0F, "Dec"),
        LineData(10F, "Jan"),
    )
}