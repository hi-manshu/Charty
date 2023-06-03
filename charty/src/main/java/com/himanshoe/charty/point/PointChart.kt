package com.himanshoe.charty.point

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.common.config.AxisConfig
import com.himanshoe.charty.common.config.ChartColors
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.common.math.chartDataToOffset
import com.himanshoe.charty.common.ui.drawGridLines
import com.himanshoe.charty.common.ui.drawXAxis
import com.himanshoe.charty.common.ui.drawXAxisLabels
import com.himanshoe.charty.common.ui.drawYAxis
import com.himanshoe.charty.common.ui.drawYAxisLabels
import com.himanshoe.charty.point.config.PointType
import com.himanshoe.charty.point.model.Point
import com.himanshoe.charty.point.model.PointData
import com.himanshoe.charty.point.model.maxYValue
import com.himanshoe.charty.point.model.minYValue

@Composable
fun PointChart(
    pointData: PointData,
    contentColor: Color,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    padding: Dp = 16.dp,
    pointType: PointType = PointType.Stroke(),
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    radiusScale: Float = 0.02f,
) {
    PointChart(
        pointData = pointData,
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

@Composable
fun PointChart(
    pointData: PointData,
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    pointType: PointType = PointType.Stroke(),
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    radiusScale: Float = 0.02f,
    chartColors: ChartColors = ChartDefaults.colorDefaults(),
) {
    val points = pointData.points
    val xLabels = points.map { it.xValue }

    var chartWidth by remember { mutableStateOf(0F) }
    var chartHeight by remember { mutableStateOf(0F) }
    var pointBound by remember { mutableStateOf(0F) }

    val horizontalScale = chartWidth.div(points.count())
    val verticalScale = chartHeight.div((pointData.maxYValue() - pointData.minYValue()))

    BoxWithConstraints(
        modifier = modifier
            .padding(padding.times(2))
            .drawBehind {
                if (points.count() >= 14 && axisConfig.showGridLabel) {
                    drawXAxisLabels(
                        data = xLabels,
                        count = points.count(),
                        padding = padding.toPx(),
                        minLabelCount = axisConfig.minLabelCount
                    )
                    drawYAxisLabels(
                        pointData.points.map { it.yValue },
                        spacing = padding.toPx(),
                    )
                }
            }
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        val contentColor = Brush.linearGradient(chartColors.contentColor)
        val backgroundColor = Brush.linearGradient(chartColors.backgroundColors)

        val minYValue = pointData.minYValue()

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
                val centerOffset =
                    chartDataToOffset(index, pointBound, size, point.yValue, horizontalScale)

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
                        minLabelCount = axisConfig.minLabelCount,
                    )
                }
            }
        }
    }
}

@Composable
fun PointChartPreview(modifier: Modifier = Modifier) {
    Column(modifier) {
        PointChart(
            pointData = PointData(generateMockPointList()),
            modifier = Modifier
                .size(450.dp),
            contentColor = Color.Red,
        )
    }
}

private fun generateMockPointList(): List<Point> {
    return listOf(
        Point(-10F, "Jan"),
        Point(10F, "Feb"),
        Point(05F, "Mar"),
        Point(50F, "Apr"),
        Point(03F, "June"),
        Point(9F, "July"),
        Point(40F, "Aug"),
        Point(60F, "Sept"),
        Point(33F, "Oct"),
        Point(11F, "Nov"),
        Point(27F, "Dec"),
        Point(10F, "Jan"),
        Point(73F, "Oct"),
        Point(-20F, "Nov"),
        Point(0F, "Dec"),
        Point(10F, "Jan"),
    )
}