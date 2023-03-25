package com.himanshoe.charty.linearregression

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.axis.AxisConfigDefaults
import com.himanshoe.charty.common.axis.drawSetXAxisWithLabels
import com.himanshoe.charty.common.axis.drawYAxisWithScaledLabels
import com.himanshoe.charty.common.calculations.unboundDataToOffset
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.common.dimens.ChartDimensDefaults
import com.himanshoe.charty.common.label.XLabels
import com.himanshoe.charty.common.label.XLabelsDefaults
import com.himanshoe.charty.common.label.YLabels
import com.himanshoe.charty.common.label.YLabelsDefaults
import com.himanshoe.charty.linearregression.calculations.betaSlopeCalculation
import com.himanshoe.charty.linearregression.calculations.calculateRegressionEndPoints
import com.himanshoe.charty.linearregression.calculations.yInterceptCalculation
import com.himanshoe.charty.linearregression.config.LinearRegressionConfig
import com.himanshoe.charty.linearregression.config.LinearRegressionDefaults
import com.himanshoe.charty.linearregression.model.*
import com.himanshoe.charty.point.cofig.PointType

/**
 * A chart that calculates the regression of the given set of data.
 *
 * This chart accepts a list of [LinearRegressionData] and calculates and plots the data's regression line.
 *
 * @param linearRegressionData the list of [LinearRegressionData] points that will be plotted as points.
 * These data are also used to calculate the linear regression.
 * @param scatterColors a list of colors to use as a gradient. The gradient will be applied from point to point
 * rather than within each point.
 * @param lineColors a list of colors to use as a gradient. The gradient will be applied to the regression line.
 * @param modifier the [Modifier] to be applied to this slider.
 * @param chartDimens the [ChartDimens] to be applied to the canvas.
 * @param axisConfig the [AxisConfig] to be applied to the chart's axes.
 * @param yLabelConfig the [YLabels] to be applied to the y-axis's labels and lines.
 * @param xLabelConfig the [XLabels] to be applied to the x-axis's labels and lines.
 * @param linearRegressionConfig the [LinearRegressionConfig] to be applied to the data points and regression line.
 */
@Composable
fun LinearRegressionChart(
    linearRegressionData: List<LinearRegressionData>,
    scatterColors: List<Color>,
    lineColors: List<Color>,
    modifier: Modifier = Modifier,
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(isSystemInDarkTheme()),
    yLabelConfig: YLabels = YLabelsDefaults.yLabelsDefaults(),
    xLabelConfig: XLabels = XLabelsDefaults.xLabelsDefaults(),
    linearRegressionConfig: LinearRegressionConfig = LinearRegressionDefaults.linearRegressionDefaults()
) {
    val data = linearRegressionData
        .sortedBy { it.xValue }
        .groupBy(keySelector = { it.xValue }, valueTransform = { it.yValue })

    val betaSlope = betaSlopeCalculation(data = linearRegressionData)
    val yIntercept = yInterceptCalculation(data = linearRegressionData, betaSlope = betaSlope)
    val regressionData =
        calculateRegressionEndPoints(data = data, betaSlope = betaSlope, yIntercept = yIntercept)

    val maxYValue by remember { derivedStateOf { maxOf(regressionData.maxYValue(), linearRegressionData.maxYValue()) } }
    val adjustedMaxYValue = maxYValue.plus(maxYValue.times(yLabelConfig.maxValueAdjustment.factor))

    val minYValueState by remember { derivedStateOf { minOf(regressionData.minYValue(), linearRegressionData.minYValue()) } }
    val minYValue = if (yLabelConfig.isBaseZero) 0f else minYValueState
    val adjustedMinYValue = minYValue.minus(minYValue.times(yLabelConfig.minValueAdjustment.factor))

    val maxXValue by remember { derivedStateOf { linearRegressionData.maxXValue() } }
    val minXValue by remember { derivedStateOf { linearRegressionData.minXValue() } }

    val xRange = maxXValue.minus(minXValue)
    val yRange = adjustedMaxYValue.minus(adjustedMinYValue)
    val adjustedXRange = xRange.plus(xRange.times(xLabelConfig.rangeAdjustment.factor))
    val adjustedYRange = yRange.plus(yRange.times(yLabelConfig.rangeAdjustment.factor))

    Canvas(modifier = modifier.padding(horizontal = chartDimens.padding)) {
        val scatterBrush = Brush.linearGradient(scatterColors)
        val lineBrush = Brush.linearGradient(lineColors)
        val scatterRadius = linearRegressionConfig.pointSize.toPx()
        val strokeWidth = linearRegressionConfig.strokeSize.toPx()
        val path = Path().apply {
            moveTo(0f, size.height)
        }

        if (axisConfig.showAxis) {
            drawYAxisWithScaledLabels(
                axisConfig = axisConfig,
                yLabelConfig = yLabelConfig,
                maxValue = adjustedMaxYValue,
                minValue = adjustedMinYValue,
                range = adjustedYRange
            )
        }

        data.entries.forEachIndexed { index, functionData ->
            functionData.value.forEach { yValue ->
                val scatterCenterOffset = unboundDataToOffset(
                    size = size,
                    xData = functionData.key,
                    xMax = maxXValue,
                    xRange = adjustedXRange,
                    yData = yValue,
                    yMax = adjustedMaxYValue,
                    yRange = adjustedYRange
                )
                val style = when (linearRegressionConfig.pointType) {
                    is PointType.Stroke -> Stroke(width = size.width.div(100))
                    else -> Fill
                }

                if (data.size > 1) {
                    when (index) {
                        0 -> {
                            val lineCenterOffset = unboundDataToOffset(
                                size = size,
                                xData = regressionData.first().xValue,
                                xMax = maxXValue,
                                xRange = adjustedXRange,
                                yData = regressionData.first().yValue,
                                yMax = adjustedMaxYValue,
                                yRange = adjustedYRange
                            )

                            path.moveTo(lineCenterOffset.x, lineCenterOffset.y)
                        }
                        data.size - 1 -> {
                            val lineCenterOffset = unboundDataToOffset(
                                size = size,
                                xData = regressionData.last().xValue,
                                xMax = maxXValue,
                                xRange = adjustedXRange,
                                yData = regressionData.last().yValue,
                                yMax = adjustedMaxYValue,
                                yRange = adjustedYRange
                            )
                            path.lineTo(lineCenterOffset.x, lineCenterOffset.y)
                        }
                    }
                }

                drawCircle(
                    center = scatterCenterOffset,
                    style = style,
                    radius = scatterRadius,
                    brush = scatterBrush
                )
            }
        }

        if (axisConfig.showXLabels) {
            drawSetXAxisWithLabels(
                maxValue = maxXValue,
                minValue = minXValue,
                range = adjustedXRange,
                xLabelConfig = xLabelConfig,
                axisConfig = axisConfig
            )
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

/**
 * A chart that calculates the regression of the given set of data.
 *
 * This chart accepts a list of [LinearRegressionData] and calculates and plots the data's regression line.
 *
 * @param linearRegressionData the list of [LinearRegressionData] points that will be plotted as points.
 * These data are also used to calculate the linear regression.
 * @param scatterColor the color to be applied to data points.
 * @param lineColor the color to be applied to the regression line.
 * @param modifier the [Modifier] to be applied to this slider.
 * @param chartDimens the [ChartDimens] to be applied to the canvas.
 * @param axisConfig the [AxisConfig] to be applied to the chart's axes.
 * @param yLabelConfig the [YLabels] to be applied to the y-axis's labels and lines.
 * @param xLabelConfig the [XLabels] to be applied to the x-axis's labels and lines.
 * @param linearRegressionConfig the [LinearRegressionConfig] to be applied to the data points and regression line.
 */
@Composable
fun LinearRegressionChart(
    linearRegressionData: List<LinearRegressionData>,
    scatterColor: Color,
    lineColor: Color,
    modifier: Modifier = Modifier,
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(isSystemInDarkTheme()),
    yLabelConfig: YLabels = YLabelsDefaults.yLabelsDefaults(),
    xLabelConfig: XLabels = XLabelsDefaults.xLabelsDefaults(),
    linearRegressionConfig: LinearRegressionConfig = LinearRegressionDefaults.linearRegressionDefaults()
) {
    LinearRegressionChart(
        linearRegressionData = linearRegressionData,
        scatterColors = listOf(scatterColor, scatterColor),
        lineColors = listOf(lineColor, lineColor),
        modifier = modifier,
        chartDimens = chartDimens,
        axisConfig = axisConfig,
        yLabelConfig = yLabelConfig,
        xLabelConfig = xLabelConfig,
        linearRegressionConfig = linearRegressionConfig
    )
}
