package com.himanshoe.charty.linearregression

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.himanshoe.charty.common.axis.*
import com.himanshoe.charty.common.axis.AxisConfigDefaults
import com.himanshoe.charty.common.axis.drawSetXAxisWithLabels
import com.himanshoe.charty.common.axis.drawYAxisWithScaledLabels
import com.himanshoe.charty.common.calculations.unboundDataToOffset
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.common.dimens.ChartDimensDefaults
import com.himanshoe.charty.linearregression.config.LinearRegressionConfig
import com.himanshoe.charty.linearregression.config.LinearRegressionDefaults
import com.himanshoe.charty.linearregression.model.*
import com.himanshoe.charty.point.cofig.PointType

// TODO: Calculate regression values in composable?

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
        .sortedBy { it.xValue.toString().toFloat() }
        .groupBy(keySelector = { it.xValue }, valueTransform = { DependentValues(it.yPointValue, it.yLineValue) })

    val maxYValue by remember { derivedStateOf { linearRegressionData.maxYValue() } }
    val adjustedMaxYValue = maxYValue.plus(maxYValue.times(yLabelConfig.maxValueAdjustment.factor))

    val minYValueState = remember { derivedStateOf { linearRegressionData.minYValue() } }
    val minYValue = if (yLabelConfig.isBaseZero) 0f else minYValueState.value
    val adjustedMinYValue = minYValue.minus(minYValue.times(yLabelConfig.minValueAdjustment.factor))

    val maxXValue by remember { derivedStateOf { linearRegressionData.maxXValue() } }
    val minXValue by remember { derivedStateOf { linearRegressionData.minXValue() } }

    val xRange = maxXValue.minus(minXValue)
    val yRange = adjustedMaxYValue.minus(adjustedMinYValue)
    val adjustedXRange = xRange.plus(xRange.times(xLabelConfig.rangeAdjustment.factor))
    val adjustedYRange = yRange.plus(yRange.times(yLabelConfig.rangeAdjustment.factor))

    Canvas(
        modifier = modifier
            .drawBehind {
                if (axisConfig.showAxis) {
                    drawYAxisWithScaledLabels(
                        axisConfig = axisConfig,
                        maxValue = adjustedMaxYValue,
                        minValue = adjustedMinYValue,
                        range = adjustedYRange,
                        breaks = yLabelConfig.breaks,
                        textColor = yLabelConfig.fontColor,
                        fontSize = yLabelConfig.fontSize
                    )
                }

                if (axisConfig.showXLabels) {
                    drawSetXAxisWithLabels(
                        maxValue = maxXValue,
                        minValue = minXValue,
                        range = adjustedXRange,
                        breaks = xLabelConfig.breaks,
                        textColor = xLabelConfig.fontColor,
                        fontSize = xLabelConfig.fontSize
                    )
                }
            }
            .padding(horizontal = chartDimens.padding)
    ) {
        val scatterBrush = Brush.linearGradient(scatterColors)
        val lineBrush = Brush.linearGradient(lineColors)
        val scatterRadius = linearRegressionConfig.pointSize.toPx()
        val strokeWidth = linearRegressionConfig.strokeSize.toPx()
        val path = Path().apply {
            moveTo(0f, size.height)
        }

        data.entries.forEachIndexed { index, functionData ->
            functionData.value.forEach { yValues ->
                val scatterCenterOffset = unboundDataToOffset(
                    size = size,
                    xData = functionData.key,
                    xMax = maxXValue,
                    xRange = adjustedXRange,
                    yData = yValues.yPointValue,
                    yMax = adjustedMaxYValue,
                    yRange = adjustedYRange
                )
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
                    yMax = adjustedMaxYValue,
                    yRange = adjustedYRange
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
    yLabelConfig: YLabels = YLabelsDefaults.yLabelsDefaults(),
    xLabelConfig: XLabels = XLabelsDefaults.xLabelsDefaults(),
    linearRegressionConfig: LinearRegressionConfig = LinearRegressionDefaults.linearRegressionDefaults()
) {
    LinearRegressionChart(
        linearRegressionData = linearRegressionData,
        scatterColors = listOf(scatterColors, scatterColors),
        lineColors = listOf(lineColors, lineColors),
        modifier = modifier,
        chartDimens = chartDimens,
        axisConfig = axisConfig,
        yLabelConfig = yLabelConfig,
        xLabelConfig = xLabelConfig,
        linearRegressionConfig = linearRegressionConfig
    )
}