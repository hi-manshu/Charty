package com.himanshoe.charty.horizontalbar

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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.common.dimens.ChartDimensDefaults
import com.himanshoe.charty.horizontalbar.axis.HorizontalAxisConfig
import com.himanshoe.charty.horizontalbar.axis.HorizontalAxisConfigDefaults
import com.himanshoe.charty.horizontalbar.axis.horizontalYAxis
import com.himanshoe.charty.horizontalbar.common.drawHorizontalBarLabel
import com.himanshoe.charty.horizontalbar.common.getBottomLeft
import com.himanshoe.charty.horizontalbar.common.getTopLeft
import com.himanshoe.charty.horizontalbar.config.HorizontalBarConfig
import com.himanshoe.charty.horizontalbar.config.HorizontalBarConfigDefaults
import com.himanshoe.charty.horizontalbar.config.StartDirection
import com.himanshoe.charty.horizontalbar.model.HorizontalBarData
import com.himanshoe.charty.horizontalbar.model.maxXValue

@Composable
fun HorizontalBarChart(
    horizontalBarData: List<HorizontalBarData>,
    color: Color,
    onBarClick: (HorizontalBarData) -> Unit,
    modifier: Modifier = Modifier,
    barDimens: ChartDimens = ChartDimensDefaults.horizontalChartDimesDefaults(),
    horizontalAxisConfig: HorizontalAxisConfig = HorizontalAxisConfigDefaults.axisConfigDefaults(),
    horizontalBarConfig: HorizontalBarConfig = HorizontalBarConfigDefaults.horizontalBarConfig()
) {
    HorizontalBarChart(
        horizontalBarData = horizontalBarData,
        colors = listOf(color, color),
        onBarClick = onBarClick,
        modifier = modifier,
        barDimens = barDimens,
        horizontalAxisConfig = horizontalAxisConfig,
        horizontalBarConfig = horizontalBarConfig
    )
}

@Composable
fun HorizontalBarChart(
    horizontalBarData: List<HorizontalBarData>,
    colors: List<Color>,
    onBarClick: (HorizontalBarData) -> Unit,
    modifier: Modifier = Modifier,
    barDimens: ChartDimens = ChartDimensDefaults.horizontalChartDimesDefaults(),
    horizontalAxisConfig: HorizontalAxisConfig = HorizontalAxisConfigDefaults.axisConfigDefaults(),
    horizontalBarConfig: HorizontalBarConfig = HorizontalBarConfigDefaults.horizontalBarConfig()
) {
    val labelTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val startAngle = if (horizontalBarConfig.startDirection == StartDirection.Left) 180F else 0F
    val maxXValueState = rememberSaveable { mutableStateOf(horizontalBarData.maxXValue()) }
    val clickedBar = remember { mutableStateOf(Offset(-10F, -10F)) }
    val maxXValue = maxXValueState.value
    val barHeight = remember { mutableStateOf(0F) }

    Canvas(
        modifier = modifier
            .drawBehind {
                if (horizontalAxisConfig.showAxes) {
                    horizontalYAxis(horizontalAxisConfig, maxXValue, startAngle)
                }
            }
            .padding(horizontal = barDimens.padding)
            .pointerInput(Unit) {
                detectTapGestures(onPress = { offset ->
                    clickedBar.value = offset
                })
            }
    ) {
        barHeight.value = size.height.div(horizontalBarData.count().times(1.2F))
        val xScalableFactor = size.width.div(maxXValue)

        when (horizontalBarConfig.startDirection) {
            StartDirection.Right -> {
                horizontalBarData.forEachIndexed { index, data ->
                    val topLeft = getTopLeft(index, barHeight, size, data, xScalableFactor)
                    val bottomLeft = getBottomLeft(index, barHeight, size, data, xScalableFactor)
                    val barWidth = data.xValue.times(xScalableFactor)

                    if (clickedBar.value.y in (topLeft.y..bottomLeft.y)) {
                        onBarClick(data)
                    }
                    drawBars(
                        data,
                        barHeight.value,
                        colors,
                        horizontalBarConfig.showLabels,
                        topLeft,
                        barWidth,
                        labelTextColor
                    )
                }
            }
            else -> {
                horizontalBarData.forEachIndexed { index, data ->
                    val barWidth = data.xValue.times(xScalableFactor)
                    val topLeft = Offset(0F, barHeight.value.times(index).times(1.2F))
                    val bottomLeft = getBottomLeft(index, barHeight, size, data, xScalableFactor)

                    if (clickedBar.value.y in (topLeft.y..bottomLeft.y)) {
                        onBarClick(data)
                    }
                    drawBars(
                        data,
                        barHeight.value,
                        colors,
                        horizontalBarConfig.showLabels,
                        topLeft,
                        barWidth,
                        labelTextColor
                    )
                }
            }
        }
    }
}

private fun DrawScope.drawBars(
    horizontalBarData: HorizontalBarData,
    barHeight: Float,
    colors: List<Color>,
    showLabels: Boolean,
    topLeft: Offset,
    barWidth: Float,
    labelTextColor :Color,
) {
    drawRoundRect(
        topLeft = topLeft,
        brush = Brush.linearGradient(colors),
        size = Size(barWidth, barHeight)
    )
    if (showLabels) {
        drawHorizontalBarLabel(
            horizontalBarData = horizontalBarData,
            barHeight = barHeight,
            topLeft = topLeft,
            labelTextColor = labelTextColor,
        )
    }
}
