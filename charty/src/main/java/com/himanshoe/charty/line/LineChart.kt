package com.himanshoe.charty.line

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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.axis.AxisConfigDefaults
import com.himanshoe.charty.common.axis.xAxis
import com.himanshoe.charty.common.axis.yAxis
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.common.dimens.ChartDimensDefaults
import com.himanshoe.charty.line.config.LineConfig
import com.himanshoe.charty.line.config.LineConfigDefaults
import com.himanshoe.charty.line.model.LineData
import com.himanshoe.charty.line.model.maxYValue

@Composable
fun LineChart(
    lineData: List<LineData>,
    color: Color,
    modifier: Modifier = Modifier,
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimensDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    lineConfig: LineConfig = LineConfigDefaults.lineConfigDefaults()
) {
    LineChart(
        lineData = lineData,
        colors = listOf(color, color),
        modifier = modifier,
        chartDimens = chartDimens,
        axisConfig = axisConfig,
        lineConfig = lineConfig
    )
}

@Composable
fun LineChart(
    lineData: List<LineData>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimensDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    lineConfig: LineConfig = LineConfigDefaults.lineConfigDefaults()
) {
    val maxYValueState = rememberSaveable { mutableStateOf(lineData.maxYValue()) }
    val maxYValue = maxYValueState.value
    val lineBound = remember { mutableStateOf(0F) }

    Canvas(modifier = modifier
        .drawBehind {
            if (axisConfig.showAxes) {
                xAxis(axisConfig, maxYValue)
                yAxis(axisConfig)
            }
        }
        .padding(horizontal = chartDimens.horizontalPadding)

    ) {
        lineBound.value = size.width.div(lineData.count().times(1.2F))
        val yChunck = size.height.div(maxYValue)
        val brush = Brush.linearGradient(colors)
        val path = Path()
        val radius = size.width.div(70)
        lineData.forEachIndexed { index, data ->
            val centerOffset = dataToOffSet(index, lineBound.value, size, data, yChunck)
            when (index) {
                0 -> path.moveTo(centerOffset.x, centerOffset.y)
                else -> path.lineTo(centerOffset.x, centerOffset.y)
            }
            if (lineConfig.hasDotMarker) {
                drawCircle(
                    center = centerOffset,
                    radius = radius,
                    brush = brush
                )
            }
            drawXLabel(data, centerOffset, radius)
        }
        val stroke = if (lineConfig.hasSmoothCurve) {
            Stroke(
                width = size.width.div(100),
                pathEffect = PathEffect.cornerPathEffect(size.width.div(100))
            )
        } else {
            Stroke(width = size.width.div(100))
        }
        drawPath(
            path = path,
            brush = brush,
            style = stroke
        )
    }
}

private fun DrawScope.drawXLabel(data: LineData, centerOffset: Offset, radius: Float) {
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

private fun dataToOffSet(
    index: Int,
    bound: Float,
    size: Size,
    data: LineData,
    yChunck: Float
): Offset {
    val startX = index.times(bound.times(1.2F))
    val endX = index.plus(1).times(bound.times(1.2F))
    val y = size.height.minus(data.yValue.times(yChunck))
    return Offset(((startX.plus(endX)).div(2F)), y)
}
