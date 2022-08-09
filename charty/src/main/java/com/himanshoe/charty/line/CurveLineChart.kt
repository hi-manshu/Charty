package com.himanshoe.charty.line

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.axis.AxisConfigDefaults
import com.himanshoe.charty.common.axis.xAxis
import com.himanshoe.charty.common.axis.yAxis
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.common.dimens.ChartDimensDefaults
import com.himanshoe.charty.line.config.CurveLineConfig
import com.himanshoe.charty.line.config.CurveLineConfigDefaults
import com.himanshoe.charty.line.model.LineData
import com.himanshoe.charty.line.model.maxYValue

@Composable
fun CurveLineChart(
    lineData: List<LineData>,
    color: Color,
    modifier: Modifier = Modifier,
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    curveLineConfig: CurveLineConfig = CurveLineConfigDefaults.curveLineConfigDefaults()
) {
    CurveLineChart(
        modifier = modifier,
        lineData = lineData,
        colors = listOf(color, color),
        chartDimens = chartDimens,
        axisConfig = axisConfig,
        curveLineConfig = curveLineConfig
    )
}

@Composable
fun CurveLineChart(
    lineData: List<LineData>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    curveLineConfig: CurveLineConfig = CurveLineConfigDefaults.curveLineConfigDefaults()
) {
    val graphPathPoints = mutableListOf<PointF>()
    val backgroundPathPoints = mutableListOf<PointF>()
    val lineBound = remember { mutableStateOf(0F) }
    val maxYValueState = rememberSaveable { mutableStateOf(lineData.maxYValue()) }
    val maxYValue = maxYValueState.value

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = chartDimens.horizontalPadding)
            .drawBehind {
                if (axisConfig.showAxes) {
                    xAxis(axisConfig, maxYValue)
                    yAxis(axisConfig)
                }
            },
        onDraw = {
            val xScaleFactor = size.width.div(lineData.size)
            val yScaleFactor = size.height.div(maxYValue)
            val canvasSize = size
            val radius = size.width.div(70)

            lineBound.value = size.width.div(lineData.count().times(1.2F))

            val lineDataItems: List<Offset> = lineData.mapIndexed { index, data ->
                dataToOffSet(
                    index = index,
                    bound = lineBound.value,
                    size = size,
                    data = data,
                    scaleFactor = yScaleFactor
                )
            }.toMutableList().also {
                it.add(Offset(canvasSize.width, canvasSize.height))
            }
            val offsetItems: List<Offset> = mutableListOf<Offset>().apply {
                add(Offset(0f, canvasSize.height))
                addAll(lineDataItems)
            }

            val xValues = offsetItems.map { it.x }
            val pointsPath = Path()
            offsetItems.forEachIndexed { index, offset ->
                val canDrawCircle = curveLineConfig.hasDotMarker && index != 0 && index != offsetItems.size.minus(1)
                if (canDrawCircle) {
                    drawCircle(
                        color = curveLineConfig.dotColor,
                        radius = radius,
                        center = Offset(offset.x, offset.y)
                    )
                }
                if (index > 0) {
                    storePoints(
                        graphPathPoints,
                        backgroundPathPoints,
                        offset,
                        offsetItems[index.minus(1)]
                    )
                }
            }

            pointsPath.apply {
                reset()
                moveTo(offsetItems.first().x, offsetItems.first().y)
                (0.until(offsetItems.size .minus(1))).forEach { index ->
                    cubicTo(
                        graphPathPoints[index].x, graphPathPoints[index].y,
                        backgroundPathPoints[index].x, backgroundPathPoints[index].y,
                        offsetItems[index.plus(1)].x, offsetItems[index.plus(1)].y
                    )
                }
            }

            val backgroundPath = android.graphics.Path(pointsPath.asAndroidPath())
                .asComposePath()
                .apply {
                    lineTo(xScaleFactor.times(xValues.last()), size.height.minus(yScaleFactor))
                    lineTo(xScaleFactor, size.height - yScaleFactor)
                    close()
                }
            drawPath(
                path = backgroundPath,
                brush = Brush.verticalGradient(
                    colors = colors,
                    endY = size.height - yScaleFactor
                ),
            )
            drawPath(
                path = pointsPath,
                color = Color.Black,
                style = Stroke(
                    width = 5F,
                    cap = StrokeCap.Round
                )
            )
        }
    )
}

private fun storePoints(
    controlPoints1: MutableList<PointF>,
    controlPoints2: MutableList<PointF>,
    firstOffset: Offset,
    previousOffset: Offset
) {
    controlPoints1.add(
        PointF(
            (firstOffset.x + previousOffset.x) / 2,
            previousOffset.y
        )
    )
    controlPoints2.add(
        PointF(
            (firstOffset.x + previousOffset.x) / 2,
            firstOffset.y
        )
    )
}

private fun dataToOffSet(
    index: Int,
    bound: Float,
    size: Size,
    data: LineData,
    scaleFactor: Float,
): Offset {
    val startX = index.times(bound.times(1.2F))
    val endX = index.plus(1).times(bound.times(1.2F))
    val y = size.height.minus(data.yValue.times(scaleFactor))
    return Offset(((startX.plus(endX)).div(2F)), y)
}
