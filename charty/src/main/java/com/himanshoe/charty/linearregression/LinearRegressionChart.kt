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
import com.himanshoe.charty.common.axis.drawXLabel
import com.himanshoe.charty.common.axis.drawYAxisWithLabels
import com.himanshoe.charty.common.calculations.dataToOffSet
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.common.dimens.ChartDimensDefaults
import com.himanshoe.charty.linearregression.config.LinearRegressionConfig
import com.himanshoe.charty.linearregression.config.LinearRegressionDefaults
import com.himanshoe.charty.linearregression.model.LinearRegressionData
import com.himanshoe.charty.linearregression.model.maxYValue
import com.himanshoe.charty.point.cofig.PointType

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
    val maxYValueState = remember { derivedStateOf { linearRegressionData.maxYValue() } }
    val maxYValue = maxYValueState.value
    val chartBound = remember { mutableStateOf(0F) }

    Canvas(
        modifier = modifier
            .drawBehind {
                if (axisConfig.showAxis) {
                    drawYAxisWithLabels(axisConfig, maxYValue, textColor = axisConfig.textColor)
                }
            }
            .padding(horizontal = chartDimens.padding)
    ) {
        chartBound.value = size.width.div(linearRegressionData.count().times(1.2F))
        val yScaleFactor = size.height.div(maxYValue)
        val scatterBrush = Brush.linearGradient(scatterColors)
        val lineBrush = Brush.linearGradient(lineColors)
        val radius = size.width.div(70)
        val strokeWidth = linearRegressionConfig.strokeSize.toPx()
        val path = Path().apply {
            moveTo(0f, size.height)
        }

        linearRegressionData.forEachIndexed { index, data ->
            val scatterCenterOffset =
                dataToOffSet(index, chartBound.value, size, data.yPointValue, yScaleFactor)
            val style = when (linearRegressionConfig.pointType) {
                is PointType.Stroke -> Stroke(width = size.width.div(100))
                else -> Fill
            }
            val lineCenterOffset = dataToOffSet(index, chartBound.value, size, data.yLineValue, yScaleFactor)

            if (linearRegressionData.size > 1) {
                when (index) {
                    0 -> path.moveTo(lineCenterOffset.x, lineCenterOffset.y)
                    linearRegressionData.size - 1 -> path.lineTo(lineCenterOffset.x, lineCenterOffset.y)
                }
            }

            drawCircle(
                center = scatterCenterOffset, style = style, radius = radius, brush = scatterBrush
            )

            if (axisConfig.showXLabels) {
                drawXLabel(
                    data = data.xValue,
                    centerOffset = scatterCenterOffset,
                    radius = radius,
                    count = linearRegressionData.count(),
                    textColor = axisConfig.textColor
                )
            }
        }
        if (linearRegressionData.size > 1) {
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