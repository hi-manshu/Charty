package com.himanshoe.charty.bar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import com.himanshoe.charty.bar.common.calculations.getTopLeft
import com.himanshoe.charty.bar.common.calculations.getTopRight
import com.himanshoe.charty.bar.common.component.drawBarLabel
import com.himanshoe.charty.bar.config.BarConfig
import com.himanshoe.charty.bar.config.BarConfigDefaults
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.bar.model.maxYValue
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.axis.AxisConfigDefaults
import com.himanshoe.charty.common.axis.drawYAxisWithLabels
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.common.dimens.ChartDimensDefaults

@Composable
fun BarChart(
    barData: List<BarData>,
    color: Color,
    onBarClick: (BarData) -> Unit,
    modifier: Modifier = Modifier,
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(isSystemInDarkTheme()),
    barConfig: BarConfig = BarConfigDefaults.barConfigDimesDefaults()
) {
    BarChart(
        barData = barData,
        colors = listOf(color, color),
        onBarClick = onBarClick,
        modifier = modifier,
        chartDimens = chartDimens,
        axisConfig = axisConfig,
        barConfig = barConfig
    )
}

@Composable
fun BarChart(
    barData: List<BarData>,
    colors: List<Color>,
    onBarClick: (BarData) -> Unit,
    modifier: Modifier = Modifier,
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(isSystemInDarkTheme()),
    barConfig: BarConfig = BarConfigDefaults.barConfigDimesDefaults()
) {
    val maxYValueState = rememberSaveable { mutableStateOf(barData.maxYValue()) }
    val clickedBarState = remember { mutableStateOf(Offset(-10F, -10F)) }
    val barWidthState = remember { mutableStateOf(0F) }

    Canvas(modifier = modifier
            // draw axis in background
            .drawBehind {
                if (axisConfig.showAxis) {
                    barChartBackground(
                        axisConfig = axisConfig,
                        maxYValue = maxYValueState.value,
                    )
                }
            }
            // add padding to canvas
            .padding(horizontal = chartDimens.padding)
            .pointerInput(Unit) {
                detectTapGestures(onPress = { offset ->
                    clickedBarState.value = offset
                })
            }
    ) {
        barWidthState.value =
            barChart(
                barData = barData,
                colors = colors,
                onBarClick = onBarClick,
                axisConfig = axisConfig,
                barConfig = barConfig,
                clickedBar = clickedBarState.value,
                maxYValue = maxYValueState.value
            )
    }
}

fun DrawScope.barChartBackground(
    axisConfig: AxisConfig,
    maxYValue: Float,
) {
    drawYAxisWithLabels(
        axisConfig = axisConfig,
        maxValue = maxYValue,
        textColor = axisConfig.textColor
    )
}

fun DrawScope.barChart(
    barData: List<BarData>,
    colors: List<Color>,
    onBarClick: (BarData) -> Unit,
    axisConfig: AxisConfig,
    barConfig: BarConfig,
    clickedBar: Offset,
    maxYValue: Float,
) : Float {
    val barWidth = size.width.div(barData.count().times(1.2F))
    val yScalableFactor = size.height.div(maxYValue)

    drawIntoCanvas {

        barData.forEachIndexed { index, data ->
            val topLeft = getTopLeft(index, barWidth, size, data.yValue, yScalableFactor)
            val topRight = getTopRight(index, barWidth, size, data.yValue, yScalableFactor)
            val barHeight = data.yValue.times(yScalableFactor)

            if (clickedBar.x in (topLeft.x..topRight.x)) {
                onBarClick(data)
            }
            drawRoundRect(
                cornerRadius = CornerRadius(if (barConfig.hasRoundedCorner) barHeight else 0F),
                topLeft = topLeft,
                brush = Brush.linearGradient(colors),
                size = Size(barWidth, barHeight)
            )

            if (axisConfig.showXLabels) {
                drawBarLabel(
                    data.xValue,
                    barWidth,
                    barHeight,
                    topLeft,
                    barData.count(),
                    axisConfig.textColor
                )
            }
        }
    }
    return barWidth
}
