package com.himanshoe.charty.line

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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
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

        val path = Path()
        lineData.forEachIndexed { index, data ->
            val centerOffset = dataToOffSet(index, lineBound.value, size, data, yChunck)
            when (index) {
                0 -> path.moveTo(centerOffset.x, centerOffset.y)
                else -> path.lineTo(centerOffset.x, centerOffset.y)
            }
            if (lineConfig.hasDotMarker) {
                drawCircle(center = centerOffset, radius = size.width.div(70), color = color)
            }
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
            color = color,
            style = stroke
        )
    }
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
        lineData.forEachIndexed { index, data ->
            val centerOffset = dataToOffSet(index, lineBound.value, size, data, yChunck)
            when (index) {
                0 -> path.moveTo(centerOffset.x, centerOffset.y)
                else -> path.lineTo(centerOffset.x, centerOffset.y)
            }
            if (lineConfig.hasDotMarker) {
                drawCircle(
                    center = centerOffset,
                    radius = size.width.div(70),
                    brush = brush
                )
            }
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
