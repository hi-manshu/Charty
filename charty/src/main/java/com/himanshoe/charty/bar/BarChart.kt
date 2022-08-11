package com.himanshoe.charty.bar

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import com.himanshoe.charty.bar.config.BarConfig
import com.himanshoe.charty.bar.config.BarConfigDefaults
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.bar.model.maxYValue
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.axis.AxisConfigDefaults
import com.himanshoe.charty.common.axis.xAxis
import com.himanshoe.charty.common.axis.yAxis
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.common.dimens.ChartDimensDefaults

@Composable
fun BarChart(
    barData: List<BarData>,
    color: Color,
    onBarClick: (BarData) -> Unit,
    modifier: Modifier = Modifier,
    barDimens: ChartDimens = ChartDimensDefaults.chartDimensDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    barConfig: BarConfig = BarConfigDefaults.barConfigDimesDefaults()
) {
    BarChart(
        barData = barData,
        colors = listOf(color, color),
        onBarClick = onBarClick,
        modifier = modifier,
        barDimens = barDimens,
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
    barDimens: ChartDimens = ChartDimensDefaults.chartDimensDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    barConfig: BarConfig = BarConfigDefaults.barConfigDimesDefaults()
) {

    val maxYValueState = rememberSaveable { mutableStateOf(barData.maxYValue()) }
    val clickedBar = remember {
        mutableStateOf(Offset(-10F, -10F))
    }

    val maxYValue = maxYValueState.value
    val barWidth = remember { mutableStateOf(0F) }

    Canvas(modifier = modifier
        .drawBehind {
            if (axisConfig.showAxes) {
                xAxis(axisConfig, maxYValue)
                yAxis(axisConfig)
            }
        }
        .padding(horizontal = barDimens.horizontalPadding)
        .pointerInput(Unit) {
            detectTapGestures(onPress = { offset ->
                clickedBar.value = offset
            })
        }
    ) {
        barWidth.value = size.width.div(barData.count().times(1.2F))
        val yScalableFactor = size.height.div(maxYValue)

        barData.forEachIndexed { index, data ->
            val topLeft = getTopLeft(index, barWidth, size, data, yScalableFactor)
            val topRight = getTopRight(index, barWidth, size, data, yScalableFactor)
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
            // draw label
            drawBarLabel(data,barWidth,barHeight,topLeft)
        }
    }
}

private fun DrawScope.drawBarLabel(
    data: BarData,
    barWidth: MutableState<Float>,
    barHeight: Float,
    topLeft: Offset
) {
    drawIntoCanvas {
        it.nativeCanvas.apply {
            drawText(
                data.xValue.toString(),
                topLeft.x.plus(barWidth.value.div(2)),
                topLeft.y.plus(barHeight.plus(barWidth.value.div(2))),
                Paint().apply {
                    textSize = size.width.div(30)
                    textAlign = Paint.Align.CENTER
                }
            )
        }
    }
}

private fun getTopLeft(
    index: Int,
    barWidth: MutableState<Float>,
    size: Size,
    barData: BarData,
    yChunck: Float
) = Offset(
    x = index.times(barWidth.value.times(1.2F)),
    y = size.height.minus(barData.yValue.times(yChunck))
)

private fun getTopRight(
    index: Int,
    barWidth: MutableState<Float>,
    size: Size,
    barData: BarData,
    yChunck: Float
) = Offset(
    x = index.plus(1).times(barWidth.value.times(1.2F)),
    y = size.height.minus(barData.yValue.times(yChunck))
)

