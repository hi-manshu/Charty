package com.himanshoe.charty.point

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.axis.AxisConfigDefaults
import com.himanshoe.charty.common.axis.drawXLabel
import com.himanshoe.charty.common.axis.drawYAxisWithLabels
import com.himanshoe.charty.common.calculations.dataToOffSet
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
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    pointConfig: PointConfig = PointConfigDefaults.pointConfigDefaults()
) {
    val maxYValueState = rememberSaveable { mutableStateOf(pointData.maxYValue()) }
    val maxYValue = maxYValueState.value
    val pointBound = remember { mutableStateOf(0F) }
    val labelTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    Canvas(
        modifier = modifier
            .drawBehind {
                if (axisConfig.showAxis) {
                    drawYAxisWithLabels(axisConfig, maxYValue, textColor = labelTextColor)
                }
            }
            .padding(horizontal = chartDimens.padding)

    ) {
        pointBound.value = size.width.div(pointData.count().times(1.2F))
        val yScaleFactor = size.height.div(maxYValue)
        val brush = Brush.linearGradient(colors)
        val radius = size.width.div(70)

        pointData.forEachIndexed { index, data ->
            val centerOffset =
                dataToOffSet(index, pointBound.value, size, data.yValue, yScaleFactor)
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

            if (axisConfig.showXLabels) {
                drawXLabel(
                    data = data.xValue,
                    centerOffset = centerOffset,
                    radius = radius,
                    count = pointData.count(),
                    textColor = labelTextColor
                )
            }
        }
    }
}

@Composable
fun PointChart(
    pointData: List<PointData>,
    color: Color,
    modifier: Modifier = Modifier,
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
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
