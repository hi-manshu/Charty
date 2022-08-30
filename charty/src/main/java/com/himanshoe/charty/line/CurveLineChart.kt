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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.axis.AxisConfigDefaults
import com.himanshoe.charty.common.axis.yAxis
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.common.dimens.ChartDimensDefaults
import com.himanshoe.charty.line.common.drawLineLabel
import com.himanshoe.charty.line.common.getLineTopLeft
import com.himanshoe.charty.line.config.CurveLineConfig
import com.himanshoe.charty.line.config.CurveLineConfigDefaults
import com.himanshoe.charty.line.model.LineData
import com.himanshoe.charty.line.model.maxYValue

@Composable
fun CurveLineChart(
    lineData: List<LineData>,
    chartColor: Color,
    lineColor: Color,
    modifier: Modifier = Modifier,
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    curveLineConfig: CurveLineConfig = CurveLineConfigDefaults.curveLineConfigDefaults()
) {
    CurveLineChart(
        modifier = modifier,
        lineData = lineData,
        chartColors = listOf(chartColor, chartColor),
        lineColors = listOf(lineColor, lineColor),
        chartDimens = chartDimens,
        axisConfig = axisConfig,
        curveLineConfig = curveLineConfig
    )
}

@Composable
fun CurveLineChart(
    lineData: List<LineData>,
    chartColor: Color,
    lineColor: List<Color>,
    modifier: Modifier = Modifier,
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    curveLineConfig: CurveLineConfig = CurveLineConfigDefaults.curveLineConfigDefaults()
) {
    CurveLineChart(
        modifier = modifier,
        lineData = lineData,
        chartColors = listOf(chartColor, chartColor),
        lineColors = lineColor,
        chartDimens = chartDimens,
        axisConfig = axisConfig,
        curveLineConfig = curveLineConfig
    )
}

@Composable
fun CurveLineChart(
    lineData: List<LineData>,
    chartColors: List<Color>,
    lineColor: Color,
    modifier: Modifier = Modifier,
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    curveLineConfig: CurveLineConfig = CurveLineConfigDefaults.curveLineConfigDefaults()
) {
    CurveLineChart(
        modifier = modifier,
        lineData = lineData,
        chartColors = chartColors,
        lineColors = listOf(lineColor, lineColor),
        chartDimens = chartDimens,
        axisConfig = axisConfig,
        curveLineConfig = curveLineConfig
    )
}

@Composable
fun CurveLineChart(
    lineData: List<LineData>,
    chartColors: List<Color>,
    lineColors: List<Color>,
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
            .padding(horizontal = chartDimens.padding)
            .drawBehind {
                if (axisConfig.showAxis) {
                    yAxis(axisConfig, maxYValue)
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
            }
            val offsetItems = buildList {
                add(Offset(0f, canvasSize.height))
                addAll(lineDataItems)
                add(Offset(canvasSize.width, canvasSize.height))
            }

            val xValues: List<Float> = offsetItems.map { it.x }
            val pointsPath = Path()
            offsetItems.forEachIndexed { index, offset -> // draw label
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
                (0.until(offsetItems.size.minus(1))).forEach { index ->
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
                    colors = chartColors,
                    endY = size.height - yScaleFactor
                ),
            )
            drawPath(
                path = pointsPath,
                brush = Brush.verticalGradient(colors = lineColors),
                style = Stroke(
                    width = 5F,
                    cap = StrokeCap.Round
                )
            )
            lineData.forEachIndexed { index, data ->
                val height = data.yValue.times(yScaleFactor)
                drawLabels(
                    index,
                    data,
                    height,
                    lineBound.value,
                    yScaleFactor,
                    axisConfig,
                    lineData.count()
                )
            }
        }
    )
}

private fun DrawScope.drawLabels(
    index: Int,
    data: LineData,
    height: Float,
    lineBound: Float,
    yScaleFactor: Float,
    axisConfig: AxisConfig,
    count: Int
) {
    if (axisConfig.showXLabels) {
        val topLeft = getLineTopLeft(
            index = index,
            barWidth = lineBound,
            size = size,
            lineData = data,
            yScalableFactor = yScaleFactor
        )
        drawLineLabel(
            data = data,
            lineWidth = lineBound,
            lineHeight = height,
            topLeft = topLeft,
            count = count
        )
    }
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
