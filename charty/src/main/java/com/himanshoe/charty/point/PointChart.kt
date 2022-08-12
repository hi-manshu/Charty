package com.himanshoe.charty.point

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.axis.AxisConfigDefaults
import com.himanshoe.charty.common.axis.xAxis
import com.himanshoe.charty.common.axis.yAxis
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.common.dimens.ChartDimensDefaults
import com.himanshoe.charty.point.cofig.PointConfig
import com.himanshoe.charty.point.cofig.PointConfigDefaults
import com.himanshoe.charty.point.cofig.PointType
import com.himanshoe.charty.point.model.PointData
import com.himanshoe.charty.point.model.maxYValue

@Composable
fun PointChart(
    pointData: List<PointData>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimensDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    pointConfig: PointConfig = PointConfigDefaults.pointConfigDefaults()
) {
    val maxYValueState = rememberSaveable { mutableStateOf(pointData.maxYValue()) }
    val maxYValue = maxYValueState.value
    val pointBound = remember { mutableStateOf(0F) }

    Canvas(
        modifier = modifier
            .drawBehind {
                if (axisConfig.showAxes) {
                    xAxis(axisConfig, maxYValue)
                    yAxis(axisConfig)
                }
            }
            .padding(horizontal = chartDimens.horizontalPadding)

    ) {
        pointBound.value = size.width.div(pointData.count().times(1.2F))
        val yChunck = size.height.div(maxYValue)
        val brush = Brush.linearGradient(colors)
        val radius = size.width.div(70)
        pointData.forEachIndexed { index, data ->
            val centerOffset = dataToOffSet(index, pointBound.value, size, data, yChunck)
            val style = when (pointConfig.pointType) {
                is PointType.Stroke -> Stroke(width = size.width.div(100))
                else -> Fill
            }

            drawCircle(
                center = centerOffset,
                style = style,
                radius = radius,
                brush = brush
            )
            // draw label
            drawXLabel(data, centerOffset, radius)
        }
    }
}

private fun DrawScope.drawXLabel(data: PointData, centerOffset: Offset, radius: Float) {
    drawIntoCanvas {
        it.nativeCanvas.apply {
            drawText(
                data.xValue.toString(),
                centerOffset.x,
                size.height.plus(radius.times(4)),
                Paint().apply {
                    textSize = size.width.div(30)
                    textAlign = Paint.Align.CENTER
                }
            )
        }
    }
}

@Composable
fun PointChart(
    pointData: List<PointData>,
    color: Color,
    modifier: Modifier = Modifier,
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimensDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    pointConfig: PointConfig = PointConfigDefaults.pointConfigDefaults()
) {
    PointChart(
        pointData = pointData,
        colors = listOf(color, color),
        modifier = modifier,
        chartDimens = chartDimens,
        axisConfig = axisConfig,
        pointConfig = pointConfig
    )
}

private fun dataToOffSet(
    index: Int,
    bound: Float,
    size: Size,
    data: PointData,
    yChunck: Float
): Offset {
    val startX = index.times(bound.times(1.2F))
    val endX = index.plus(1).times(bound.times(1.2F))
    val y = size.height.minus(data.yValue.times(yChunck))
    return Offset(((startX.plus(endX)).div(2F)), y)
}
