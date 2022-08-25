package com.himanshoe.charty.combined

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.himanshoe.charty.combined.common.calculations.getTopLeft
import com.himanshoe.charty.combined.common.calculations.getTopRight
import com.himanshoe.charty.combined.common.component.drawCombinedBarLabel
import com.himanshoe.charty.combined.common.component.drawLineLabels
import com.himanshoe.charty.combined.config.CombinedBarConfig
import com.himanshoe.charty.combined.config.CombinedBarConfigDefaults
import com.himanshoe.charty.combined.model.CombinedBarData
import com.himanshoe.charty.combined.model.maxYValue
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.axis.AxisConfigDefaults
import com.himanshoe.charty.common.axis.yAxis
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.common.dimens.ChartDimensDefaults

@Composable
fun CombinedBarChart(
    combinedBarData: List<CombinedBarData>,
    onClick: (CombinedBarData) -> Unit,
    barColors: List<Color>,
    lineColors: List<Color>,
    modifier: Modifier = Modifier,
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    combinedBarConfig: CombinedBarConfig = CombinedBarConfigDefaults.barConfigDimesDefaults()
) {
    val maxYValueState = rememberSaveable { mutableStateOf(combinedBarData.maxYValue()) }
    val clickedBar = remember { mutableStateOf(Offset(-10F, -10F)) }

    val maxYValue = maxYValueState.value
    val chartBound = remember { mutableStateOf(0F) }

    Canvas(
        modifier = modifier
            .drawBehind {
                if (axisConfig.showAxis) {
                    yAxis(axisConfig, maxYValue)
                }
            }
            .padding(horizontal = chartDimens.padding)
            .pointerInput(Unit) {
                detectTapGestures(onPress = { offset ->
                    clickedBar.value = offset
                })
            }
    ) {
        chartBound.value = size.width.div(combinedBarData.count().times(1.2F))
        val scaleFactor = size.height.div(maxYValue)
        val brush = Brush.linearGradient(lineColors)
        val radius = size.width.div(70)
        val strokeWidth = size.width.div(100)
        val path = Path().apply {
            moveTo(0f, size.height)
        }
        val lastIndex = combinedBarData.size.minus(1)

        combinedBarData.forEachIndexed { index, data ->
            val topLeft = getTopLeft(index, chartBound, size, data, scaleFactor)
            val topRight = getTopRight(index, chartBound, size, data, scaleFactor)
            val barHeight = data.yBarValue.times(scaleFactor)
//
            if (clickedBar.value.x in (topLeft.x..topRight.x)) {
                onClick(data)
            }
            drawRoundRect(
                cornerRadius = CornerRadius(if (combinedBarConfig.hasRoundedCorner) barHeight else 0F),
                topLeft = topLeft,
                brush = Brush.linearGradient(barColors),
                size = Size(chartBound.value, barHeight)
            )
            val centerOffset = dataToOffSet(index, chartBound.value, size, data, scaleFactor)
            val drawnPath = path.lineTo(centerOffset.x, centerOffset.y)
            if (combinedBarConfig.hasLineLabel) { drawLineLabels(centerOffset, data, combinedBarConfig.lineLabelColor) }

            when (index) {
                lastIndex -> drawnPath.also {
                    path.lineTo(size.width, size.height)
                }
                else -> drawnPath
            }
            if (combinedBarConfig.hasDotMarker) {
                drawCircle(
                    center = centerOffset,
                    radius = radius,
                    brush = brush
                )
            }

            // draw label
            drawCombinedBarLabel(data, chartBound.value, barHeight, topLeft)
            val pathEffect =
                if (combinedBarConfig.hasSmoothCurve) PathEffect.cornerPathEffect(strokeWidth) else null

            drawPath(
                path = path,
                brush = brush,
                style = Stroke(width = strokeWidth, pathEffect = pathEffect),
            )
        }
    }
}

@Composable
fun CombinedBarChart(
    combinedBarData: List<CombinedBarData>,
    onClick: (CombinedBarData) -> Unit,
    barColors: List<Color>,
    lineColor: Color,
    modifier: Modifier = Modifier,
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    combinedBarConfig: CombinedBarConfig = CombinedBarConfigDefaults.barConfigDimesDefaults()
) {
    CombinedBarChart(
        combinedBarData = combinedBarData,
        onClick = onClick,
        barColors = barColors,
        lineColors = listOf(lineColor, lineColor),
        modifier = modifier,
        axisConfig = axisConfig,
        chartDimens = chartDimens,
        combinedBarConfig = combinedBarConfig
    )
}

@Composable
fun CombinedBarChart(
    combinedBarData: List<CombinedBarData>,
    onClick: (CombinedBarData) -> Unit,
    barColor: Color,
    lineColors: List<Color>,
    modifier: Modifier = Modifier,
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    combinedBarConfig: CombinedBarConfig = CombinedBarConfigDefaults.barConfigDimesDefaults()
) {
    CombinedBarChart(
        combinedBarData = combinedBarData,
        onClick = onClick,
        barColors = listOf(barColor, barColor),
        lineColors = lineColors,
        modifier = modifier,
        axisConfig = axisConfig,
        chartDimens = chartDimens,
        combinedBarConfig = combinedBarConfig
    )
}

@Composable
fun CombinedBarChart(
    combinedBarData: List<CombinedBarData>,
    onClick: (CombinedBarData) -> Unit,
    barColor: Color,
    lineColor: Color,
    modifier: Modifier = Modifier,
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    combinedBarConfig: CombinedBarConfig = CombinedBarConfigDefaults.barConfigDimesDefaults()
) {
    CombinedBarChart(
        combinedBarData = combinedBarData,
        onClick = onClick,
        barColors = listOf(barColor, barColor),
        lineColors = listOf(lineColor, lineColor),
        modifier = modifier,
        axisConfig = axisConfig,
        chartDimens = chartDimens,
        combinedBarConfig = combinedBarConfig
    )
}

private fun dataToOffSet(
    index: Int,
    bound: Float,
    size: Size,
    data: CombinedBarData,
    yScaleFactor: Float
): Offset {
    val startX = index.times(bound.times(1.2F))
    val endX = index.plus(1).times(bound.times(1.2F))
    val y = size.height.minus(data.yLineValue.times(yScaleFactor))
    return Offset(((startX.plus(endX)).div(2F)), y)
}
