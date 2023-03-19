package com.himanshoe.charty.common.axis

import android.graphics.Paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import java.text.DecimalFormat

internal fun DrawScope.drawYAxisWithLabels(
    axisConfig: AxisConfig,
    maxValue: Float,
    isCandleChart: Boolean = false,
    textColor: Color = Color.White
) {
    val graphYAxisEndPoint = size.height.div(4)
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f), 0f)
    val labelScaleFactor = maxValue.div(4)

    repeat(5) { index ->
        val yAxisEndPoint = graphYAxisEndPoint.times(index)
        if (axisConfig.showUnitLabels) {
            drawIntoCanvas {
                it.nativeCanvas.apply {
                    drawText(
                        getLabelText(labelScaleFactor.times(4.minus(index)), isCandleChart),
                        0F.minus(25),
                        yAxisEndPoint.minus(10),
                        Paint().apply {
                            color = textColor.toArgb()
                            textSize = size.width.div(30)
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }
        }
        if (index != 0) {
            drawLine(
                start = Offset(x = 0f, y = yAxisEndPoint),
                end = Offset(x = size.width, y = yAxisEndPoint),
                color = axisConfig.xAxisColor,
                pathEffect = if (axisConfig.isAxisDashed) pathEffect else null,
                alpha = 0.1F,
                strokeWidth = size.width.div(200)
            )
        }
    }
}

internal fun DrawScope.drawYAxisWithScaledLabels(
    axisConfig: AxisConfig,
    maxValue: Float,
    minValue: Float,
    range: Float,
    breaks: Int = 5,
    isCandleChart: Boolean = false,
    textColor: Color = Color.White,
    fontSize: Float = 12f
) {
    val steps = maxValue.minus(minValue).div(breaks.minus(1))
    val labels = (0..breaks.minus(1)).map { minValue.plus(it.times(steps)) }.reversed()
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f), 0f)

    labels.forEach { label ->
        val currentYDiff = maxValue.minus(label)
        val rangeDiff = range.minus(currentYDiff)
        val y = size.height.minus(rangeDiff.div(range).times(size.height))

        drawIntoCanvas {
            it.nativeCanvas.apply {
                drawText(
                    getLabelText(label, isCandleChart),
                    0f,
                    y.minus(15),
                    Paint().apply {
                        color = textColor.toArgb()
                        textSize = fontSize
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
        }
        drawLine(
            start = Offset(x = 0f, y = y),
            end = Offset(x = size.width, y = y),
            color = axisConfig.xAxisColor,
            pathEffect = if (axisConfig.isAxisDashed) pathEffect else null,
            alpha = 0.1F,
            strokeWidth = size.width.div(200)
        )
    }
}

private fun getLabelText(value: Float, isCandleChart: Boolean): String {
    val pattern = if (isCandleChart) "#" else "#.##"
    return DecimalFormat(pattern).format(value).toString()
}

internal fun DrawScope.drawXLabel(
    data: Any,
    centerOffset: Offset,
    radius: Float,
    count: Int,
    textColor: Color = Color.Black
) {
    val divisibleFactor = if (count > 10) count else 1
    val textSizeFactor = if (count > 10) 3 else 30
    drawIntoCanvas {
        it.nativeCanvas.apply {
            drawText(
                data.toString(),
                centerOffset.x,
                size.height.plus(radius.times(4)),
                Paint().apply {
                    color = textColor.toArgb()
                    textSize = size.width.div(textSizeFactor).div(divisibleFactor)
                    textAlign = Paint.Align.CENTER
                }
            )
        }
    }
}

internal fun DrawScope.drawSetXAxisWithLabels(
    maxValue: Float,
    minValue: Float,
    range: Float,
    breaks: Int = 5,
    isCandleChart: Boolean = false,
    textColor: Color = Color.White,
    fontSize: Float = 12f
) {
    val steps = maxValue.minus(minValue).div(breaks.minus(1))
    val labels = (0..breaks.minus(1)).map { minValue.plus(it.times(steps)) }

    labels.forEach { label ->
        val currentXDiff = maxValue.minus(label)
        val rangeDiff = range.minus(currentXDiff)
        val x = rangeDiff.div(range).times(size.width)

        drawIntoCanvas {
            it.nativeCanvas.apply {
                drawText(
                    getLabelText(label, isCandleChart),
                    x,
                    size.height.plus(50f),
                    Paint().apply {
                        color = textColor.toArgb()
                        textSize = fontSize
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
        }
    }
}
