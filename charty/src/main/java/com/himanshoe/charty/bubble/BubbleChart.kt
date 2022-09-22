package com.himanshoe.charty.bubble

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import com.himanshoe.charty.bubble.config.BubbleConfig
import com.himanshoe.charty.bubble.config.BubbleConfigDefaults
import com.himanshoe.charty.bubble.model.BubbleData
import com.himanshoe.charty.bubble.model.maxVolumeSize
import com.himanshoe.charty.bubble.model.maxYValue
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.axis.AxisConfigDefaults
import com.himanshoe.charty.common.axis.yAxis
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.common.dimens.ChartDimensDefaults

@Composable
fun BubbleChart(
    bubbleData: List<BubbleData>,
    color: Color,
    modifier: Modifier = Modifier,
    bubbleConfig: BubbleConfig = BubbleConfigDefaults.bubbleConfigDefaults(),
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
) {
    BubbleChart(bubbleData, listOf(color, color), modifier, bubbleConfig, chartDimens, axisConfig)
}

@Composable
fun BubbleChart(
    bubbleData: List<BubbleData>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    bubbleConfig: BubbleConfig = BubbleConfigDefaults.bubbleConfigDefaults(),
    chartDimens: ChartDimens = ChartDimensDefaults.chartDimesDefaults(),
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
) {
    val maxYValueState = rememberSaveable { mutableStateOf(bubbleData.maxYValue()) }
    val maxVolumeState = rememberSaveable { mutableStateOf(bubbleData.maxVolumeSize()) }

    val pointBound = remember { mutableStateOf(0F) }
    val maxYValue = maxYValueState.value
    val maxVolumeValue = maxVolumeState.value

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
                    // clickedBar.value = offset
                })
            }
    ) {
        pointBound.value = size.width.div(bubbleData.count().times(1.2F))
        val yScalableFactor = size.height.div(maxYValue)
        val volumeScalableFactor = bubbleConfig.maxVolumeSize.div(maxVolumeValue)
        val brush = Brush.linearGradient(colors.map { it.copy(alpha = 0.5F) })
        bubbleData.forEachIndexed { index, data ->
            val radius = volumeScalableFactor.times(data.volumeSize)

            val centerOffset = dataToOffSet(
                index,
                pointBound.value,
                size,
                data,
                yScalableFactor
            )

            drawCircle(
                center = centerOffset,
                style = Fill,
                radius = radius,
                brush = brush
            )
            // draw label
            if (axisConfig.showXLabels) {
                drawXLabel(data, centerOffset, size.width.div(70), bubbleData.count())
            }
        }
    }
}

private fun DrawScope.drawXLabel(
    data: BubbleData,
    centerOffset: Offset,
    radius: Float,
    count: Int
) {
    val divisibleFactor = if (count > 10) count else 1
    val textSizeFactor = if (count > 10) 3 else 30
    drawIntoCanvas {
        it.nativeCanvas.apply {
            drawText(
                data.xValue.toString(),
                centerOffset.x,
                size.height.plus(radius.times(4)),
                Paint().apply {
                    textSize = size.width.div(textSizeFactor).div(divisibleFactor)
                    textAlign = Paint.Align.CENTER
                }
            )
        }
    }
}

private fun dataToOffSet(
    index: Int,
    bound: Float,
    size: Size,
    data: BubbleData,
    yScaleFactor: Float
): Offset {
    val startX = index.times(bound.times(1.2F))
    val endX = index.plus(1).times(bound.times(1.2F))
    val y = size.height.minus(data.yValue.times(yScaleFactor))
    return Offset(((startX.plus(endX)).div(2F)), y)
}
