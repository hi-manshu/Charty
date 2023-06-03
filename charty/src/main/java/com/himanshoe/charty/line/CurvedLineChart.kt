package com.himanshoe.charty.line

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

@Composable
fun CurveLineChart(
    dataCollection: ChartDataCollection,
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    radiusScale: Float = 0.02f,
    lineConfig: LineConfig = LineChartDefaults.defaultConfig(),
    chartColors: LineChartColors = LineChartDefaults.defaultColor(),
) {
    BoxWithConstraints(
        modifier = modifier
            .padding(padding.times(2))
            .drawBehind {
                if (dataCollection.data.count() >= 14 && axisConfig.showGridLabel) {
                    drawXAxisLabels(
                        data = dataCollection.data.map { it.xValue },
                        count = dataCollection.data.count(),
                        padding = padding.toPx(),
                        minLabelCount = axisConfig.minLabelCount
                    )
                    drawYAxisLabels(
                        dataCollection.data.map { it.yValue },
                        spacing = padding.toPx(),
                    )
                }
            }
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        ChartSurface(padding = padding, chartData = dataCollection)
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
                            prevInnerX + (innerX - prevInnerX) / 2, prevInnerY,
                            prevInnerX + (innerX - prevInnerX) / 2, innerY,
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
//@Composable
//fun CurveLineChart(
//    lineDataCollection: LineDataCollection,
//    modifier: Modifier = Modifier,
//    padding: Dp = 16.dp,
//    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
//    radiusScale: Float = 0.02f,
//    lineConfig: LineConfig = LineChartDefaults.defaultConfig(),
//    chartColors: LineChartColors = LineChartDefaults.defaultColor(),
//) {
//    BoxWithConstraints(
//        modifier = modifier
//            .padding(padding.times(2))
//            .drawBehind {
//                if (lineDataCollection.data.count() >= 14 && axisConfig.showGridLabel) {
//                    drawXAxisLabels(
//                        data = lineDataCollection.data.map { it.xValue },
//                        count = lineDataCollection.data.count(),
//                        padding = padding.toPx(),
//                        minLabelCount = axisConfig.minLabelCount
//                    )
//                    drawYAxisLabels(
//                        lineDataCollection.data.map { it.yValue },
//                        spacing = padding.toPx(),
//                    )
//                }
//            }
//            .background(Color.White),
//        contentAlignment = Alignment.Center
//    ) {
//        Canvas(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            val graphPathPoints = mutableListOf<PointF>()
//
//            val xStep = size.width / (lineDataCollection.data.size - 1)
//            val yStep =
//                size.height / (lineDataCollection.data.maxOf { it.yValue } - lineDataCollection.data.minOf { it.yValue })
//
//            lineDataCollection.data.forEachIndexed { index, data ->
//                val x = index * xStep
//                val y =
//                    size.height - (data.yValue - lineDataCollection.data.minOf { it.yValue }) * yStep
//                val innerX = x.coerceIn(x - radiusScale * size.width, x + radiusScale * size.width)
//                val innerY = y.coerceIn(0f, size.height)
//
//                graphPathPoints.add(PointF(innerX, innerY))
//
//                if (index > 0) {
//                    val prevX = (index - 1) * xStep
//                    val prevY =
//                        size.height - (lineDataCollection.data[index - 1].yValue - lineDataCollection.data.minOf { it.yValue }) * yStep
//                    val prevInnerX = prevX.coerceIn(
//                        prevX - radiusScale * size.width,
//                        prevX + radiusScale * size.width
//                    )
//                    val prevInnerY = prevY.coerceIn(0f, size.height)
//
//                    val path = Path().apply {
//                        moveTo(prevInnerX, prevInnerY)
//                        cubicTo(
//                            prevInnerX + (innerX - prevInnerX) / 2, prevInnerY,
//                            prevInnerX + (innerX - prevInnerX) / 2, innerY,
//                            innerX, innerY
//                        )
//                        lineTo(innerX, size.height)
//                        lineTo(prevInnerX, size.height)
//                        close()
//                    }
//
//                    drawPath(
//                        path = path,
//                        brush = Brush.linearGradient(
//                            colors = chartColors.backgroundColors,
//                        )
//                    )
//
//                    drawPath(
//                        path = path,
//                        brush = Brush.linearGradient(
//                            colors = chartColors.lineColor,
//                        ),
//                        style = Stroke(width = lineConfig.strokeSize.toPx())
//                    )
//                }
//            }
//
//            if (lineConfig.hasDotMarker) {
//                graphPathPoints.forEach { point ->
//                    drawCircle(
//                        color = chartColors.dotColor.first(),
//                        radius = radiusScale * size.width,
//                        center = Offset(point.x, point.y)
//                    )
//                }
//            }
//        }
//    }
//}