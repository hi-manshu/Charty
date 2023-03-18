package com.himanshoe.charty.linearregression

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.axis.AxisConfigDefaults
import com.himanshoe.charty.common.axis.drawSetXAxisWithLabels
import com.himanshoe.charty.common.axis.drawYAxisWithLabels
import com.himanshoe.charty.common.calculations.unboundDataToOffset
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.common.dimens.ChartDimensDefaults
import com.himanshoe.charty.linearregression.config.LinearRegressionConfig
import com.himanshoe.charty.linearregression.config.LinearRegressionDefaults
import com.himanshoe.charty.linearregression.model.*
import com.himanshoe.charty.point.cofig.PointType

// TODO: Figure out yAxis - device diff. Update to make labels more centered and relevant.
// TODO: Figure how to adjust axis labels

@Composable
fun LinearRegressionChart(
    linearRegressionData: List<LinearRegressionData>,
    scatterColors: List<Color>,
    lineColors: List<Color>,
    modifier: Modifier = Modifier,
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(isSystemInDarkTheme()),
    linearRegressionConfig: LinearRegressionConfig = LinearRegressionDefaults.linearRegressionDefaults()
) {
    val data = linearRegressionData
        .sortedBy { it.xValue.toString().toFloat() }
        .groupBy(keySelector = { it.xValue }, valueTransform = { DependentValues(it.yPointValue, it.yLineValue) })
    val maxYValueState = remember { derivedStateOf { linearRegressionData.maxYValue() } }
    val maxYValue = maxYValueState.value
    val maxXValueState = remember { derivedStateOf { linearRegressionData.maxXValue() } }
    val maxXValue = maxXValueState.value
    val minXValueState = remember { derivedStateOf { linearRegressionData.minXValue() } }
    val minXValue = minXValueState.value
    val xRange = maxXValue.minus(minXValue)
    // TODO: Make the range padding variable
    val adjustedXRange = xRange.plus(xRange.times(.1f))
    val chartBound = remember { mutableStateOf(0F) }

    Canvas(
        modifier = modifier
            .drawBehind {
                if (axisConfig.showAxis) {
                    drawYAxisWithLabels(axisConfig, maxYValue, textColor = Color.White)
                }
            }
            .padding(horizontal = chartDimens.padding)
    ) {
        println("SIZE: $size")
        chartBound.value = size.width.div(data.count().times(1.2F))
        println("BOUND: ${chartBound.value}")
        val yScaleFactor = size.height.div(maxYValue)
        val scatterBrush = Brush.linearGradient(scatterColors)
        val lineBrush = Brush.linearGradient(lineColors)
        val scatterRadius = linearRegressionConfig.pointSize.toPx()
        val strokeWidth = linearRegressionConfig.strokeSize.toPx()
        val path = Path().apply {
            moveTo(0f, size.height)
        }

        data.entries.forEachIndexed { index, functionData ->
            functionData.value.forEach { yValues ->
                // TODO: To adjust yLabels, need to adjust yOffset
                val scatterCenterOffset = unboundDataToOffset(
                    size = size,
                    xData = functionData.key,
                    xMax = maxXValue,
                    xRange = adjustedXRange,
                    yData = yValues.yPointValue,
                    yScaleFactor = yScaleFactor
                )
                println("OFFSET: $scatterCenterOffset")
                val style = when (linearRegressionConfig.pointType) {
                    is PointType.Stroke -> Stroke(width = size.width.div(100))
                    else -> Fill
                }
                val lineCenterOffset = unboundDataToOffset(
                    size = size,
                    xData = functionData.key,
                    xMax = maxXValue,
                    xRange = adjustedXRange,
                    yData = yValues.yLineValue,
                    yScaleFactor = yScaleFactor
                )

                if (data.size > 1) {
                    when (index) {
                        0 -> path.moveTo(lineCenterOffset.x, lineCenterOffset.y)
                        data.size - 1 -> path.lineTo(lineCenterOffset.x, lineCenterOffset.y)
                    }
                }

                drawCircle(
                    center = scatterCenterOffset,
                    style = style,
                    radius = scatterRadius,
                    brush = scatterBrush
                )

                // TODO: make breaks variable
                if (axisConfig.showXLabels) {
                    drawSetXAxisWithLabels(
                        maxValue = maxXValue,
                        minValue = minXValue,
                        range = adjustedXRange
                    )
                }
            }
        }
        if (data.size > 1) {
            drawPath(
                path = path,
                brush = lineBrush,
                style = Stroke(width = strokeWidth),
            )
        }
    }
}

@Composable
fun LinearRegressionChart(
    linearRegressionData: List<LinearRegressionData>,
    scatterColors: Color,
    lineColors: Color,
    modifier: Modifier = Modifier,
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(isSystemInDarkTheme()),
    linearRegressionConfig: LinearRegressionConfig = LinearRegressionDefaults.linearRegressionDefaults()
) {
    LinearRegressionChart(
        linearRegressionData = linearRegressionData,
        scatterColors = listOf(scatterColors, scatterColors),
        lineColors = listOf(lineColors, lineColors),
        modifier = modifier,
        chartDimens = chartDimens,
        axisConfig = axisConfig,
        linearRegressionConfig = linearRegressionConfig
    )
}