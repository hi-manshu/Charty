package com.himanshoe.charty.bar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
    val maxYValue by remember(barData) {
        derivedStateOf {
            barData.maxYValue()
        }
    }
    val clickedBar = remember { mutableStateOf(Offset(-10F, -10F)) }
    val barWidth = remember { mutableStateOf(0F) }

    Canvas(
        modifier = modifier
            .drawBehind {
                if (axisConfig.showAxis) {
                    drawYAxisWithLabels(
                        axisConfig = axisConfig,
                        maxValue = maxYValue,
                        textColor = axisConfig.textColor
                    )
                }
            }
            .padding(horizontal = chartDimens.padding)
            .pointerInput(Unit) {
                detectTapGestures(onPress = { offset ->
                    clickedBar.value = offset
                })
            }
    ) {
        barWidth.value = size.width.div(barData.count().times(1.2F))
        val yScalableFactor = size.height.div(maxYValue)

        barData.forEachIndexed { index, data ->
            val topLeft = getTopLeft(index, barWidth.value, size, data.yValue, yScalableFactor)
            val topRight = getTopRight(index, barWidth.value, size, data.yValue, yScalableFactor)
            val barHeight = data.yValue.times(yScalableFactor)

            if (clickedBar.value.x in (topLeft.x..topRight.x)) {
                onBarClick(data)
            }
            drawRoundRect(
                cornerRadius = CornerRadius(if (barConfig.hasRoundedCorner) barHeight else 0F),
                topLeft = topLeft,
                brush = Brush.linearGradient(colors),
                size = Size(barWidth.value, barHeight)
            )

            if (axisConfig.showXLabels) {
                drawBarLabel(
                    data.xValue,
                    barWidth.value,
                    barHeight,
                    topLeft,
                    barData.count(),
                    axisConfig.textColor
                )
            }
        }
    }
}
