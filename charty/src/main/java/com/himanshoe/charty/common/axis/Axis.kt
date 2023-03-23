package com.himanshoe.charty.common.axis

import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import com.himanshoe.charty.common.label.XLabels
import com.himanshoe.charty.common.label.YLabels
import java.text.DecimalFormat
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

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
    yLabelConfig: YLabels,
    maxValue: Float,
    minValue: Float,
    range: Float,
    isCandleChart: Boolean = false
) {
    val steps = maxValue.minus(minValue).div(yLabelConfig.breaks.minus(1))
    val labels = (0..yLabelConfig.breaks.minus(1)).map { minValue.plus(it.times(steps)) }.reversed()
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f), 0f)

    labels.forEach { label ->
        val currentYDiff = maxValue.minus(label)
        val rangeDiff = range.minus(currentYDiff)
        val y = size.height.minus(rangeDiff.div(range).times(size.height))
        val x = 0f.minus(yLabelConfig.xOffset)
        val stringLabel = getLabelText(label, isCandleChart)
        val bounds = Rect()
        val paint = Paint()
        paint.apply {
            getTextBounds(stringLabel, 0, stringLabel.length, bounds)
            color = yLabelConfig.fontColor.toArgb()
            textSize = yLabelConfig.fontSize
            textAlign = yLabelConfig.textAlignment
        }

        val rotateOffset = sin(abs(yLabelConfig.rotation).toRadians()).times(bounds.width().div(2f))
        val bottomCornerOffset = cos(45f.toRadians()).times(bounds.height())
        val centerBottomRightCorner = rotateOffset.minus(bottomCornerOffset)

        val yRotated =
            if (yLabelConfig.rotation == 0f) y.plus(bounds.height())
            else y.plus(bounds.height()).plus(centerBottomRightCorner)

        rotate(degrees = yLabelConfig.rotation, pivot = Offset(x = x, y = y)) {
            drawIntoCanvas {
                it.nativeCanvas.apply {
                    drawText(
                        stringLabel,
                        x,
                        yRotated,
                        paint
                    )
                }
            }
        }
        drawLine(
            start = Offset(x = 0f.plus(yLabelConfig.lineStartPadding), y = y),
            end = Offset(x = size.width, y = y),
            color = axisConfig.yAxisColor,
            pathEffect = if (axisConfig.isAxisDashed) pathEffect else null,
            alpha = yLabelConfig.lineAlpha,
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
    axisConfig: AxisConfig,
    xLabelConfig: XLabels,
    maxValue: Float,
    minValue: Float,
    range: Float,
    isCandleChart: Boolean = false
) {
    val steps = maxValue.minus(minValue).div(xLabelConfig.breaks.minus(1))
    val labels = (0..xLabelConfig.breaks.minus(1)).map { minValue.plus(it.times(steps)) }
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f), 0f)

    labels.forEach { label ->
        val currentXDiff = maxValue.minus(label)
        val rangeDiff = range.minus(currentXDiff)
        val x = rangeDiff.div(range).times(size.width)
        val y = size.height.plus(xLabelConfig.yOffset)
        val stringLabel = getLabelText(label, isCandleChart)
        val paint = Paint()
        val bounds = Rect()

        paint.apply {
            getTextBounds(stringLabel, 0, stringLabel.length, bounds)
            color = xLabelConfig.fontColor.toArgb()
            textSize = xLabelConfig.fontSize
            textAlign = xLabelConfig.textAlignment
        }

        val rotateOffset = sin(xLabelConfig.rotation.toRadians()).times(bounds.width().div(2f))
        val topCornerOffset = cos(45f.toRadians()).times(bounds.height().div(2f))
        val centerTopLeftCorner = rotateOffset.minus(topCornerOffset)

        val xRotated = if (xLabelConfig.rotation == 0f) x else x.plus(centerTopLeftCorner)

        rotate(degrees = xLabelConfig.rotation, pivot = Offset(x = x, y = y)) {
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    stringLabel,
                    xRotated,
                    y,
                    paint
                )
            }
        }
        if (xLabelConfig.showLines) {
            drawLine(
                start = Offset(x = x, y = 0f),
                end = Offset(x = x, y = size.height),
                color = axisConfig.xAxisColor,
                pathEffect = if (axisConfig.isAxisDashed) pathEffect else null,
                alpha = xLabelConfig.lineAlpha,
                strokeWidth = size.width.div(200)
            )
        }
    }
}

fun Float.toRadians() = this * (PI / 180f).toFloat()
