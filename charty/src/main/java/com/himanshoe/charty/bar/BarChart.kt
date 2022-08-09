package com.himanshoe.charty.bar

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
import androidx.compose.ui.input.pointer.pointerInput
import com.himanshoe.charty.bar.config.BarConfig
import com.himanshoe.charty.bar.config.BarConfigDefaults
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.bar.model.maxYValue
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.axis.AxisConfigDefaults
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.common.dimens.ChartDimensDefaults
import com.himanshoe.charty.common.xAxis
import com.himanshoe.charty.common.yAxis

@Composable
fun BarChart(
    barData: List<BarData>, color: Color,
    modifier: Modifier = Modifier,
    barDimens: ChartDimens = ChartDimensDefaults.chartDimensDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    barConfig: BarConfig = BarConfigDefaults.barConfigDimesDefaults()
) {

    val maxYValueState = rememberSaveable {
        mutableStateOf(barData.maxYValue())
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
            detectTapGestures(onPress = {

            })
        }
    ) {
        barWidth.value = size.width.div(barData.count().times(1.2F))
        val yChunck = size.height.div(maxYValue)

        barData.forEachIndexed { index, data ->
            val topLeft = getTopLeft(index, barWidth, size, data, yChunck)
            val barHeight = data.yValue.times(yChunck)

            drawRoundRect(
                cornerRadius = CornerRadius(if (barConfig.hasRoundedCorner) barHeight else 5F),
                topLeft = topLeft,
                color = color,
                size = Size(barWidth.value, barHeight)
            )
        }
    }
}

@Composable
fun BarChart(
    barData: List<BarData>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    barDimens: ChartDimens = ChartDimensDefaults.chartDimensDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    barConfig: BarConfig = BarConfigDefaults.barConfigDimesDefaults()
) {

    val maxYValueState = rememberSaveable {
        mutableStateOf(barData.maxYValue())
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
    ) {
        barWidth.value = size.width.div(barData.count().times(1.2F))
        val yChunck = size.height.div(maxYValue)

        barData.forEachIndexed { index, data ->
            val topLeft = getTopLeft(index, barWidth, size, data, yChunck)
            val barHeight = data.yValue.times(yChunck)

            drawRoundRect(
                cornerRadius = CornerRadius(if (barConfig.hasRoundedCorner) barHeight else 0F),
                topLeft = topLeft,
                brush = Brush.verticalGradient(colors),
                size = Size(barWidth.value, barHeight)
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
