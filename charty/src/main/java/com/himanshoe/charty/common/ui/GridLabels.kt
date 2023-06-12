package com.himanshoe.charty.common.ui

import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb

internal fun DrawScope.drawXAxisLabels(
    data: Any,
    center: Offset,
    count: Int,
    padding: Float,
    minLabelCount: Int,
    textColor: Color = Color.Black,
) {
    val divisibleFactor = if (count > 10) count else 1
    val textSizeFactor = if (count > 10) 3 else 30

    val textBounds = Rect()
    val textPaint = Paint().apply {
        color = textColor.toArgb()
        textSize = size.width / textSizeFactor / divisibleFactor
        textAlign = Paint.Align.CENTER
        getTextBounds(data.toString(), 0, data.toString().length, textBounds)
    }

    drawContext.canvas.nativeCanvas.drawText(
        data.toString().take(minLabelCount),
        center.x,
        size.height + padding,
        textPaint
    )
}

internal fun DrawScope.drawXAxisLabels(
    data: List<Any>,
    count: Int,
    padding: Float,
    minLabelCount: Int,
    textColor: Color = Color.Black,
) {
    val textBounds = Rect()
    val textPaint = Paint().apply {
        color = textColor.toArgb()
        textSize = size.width / 30
        textAlign = Paint.Align.CENTER
        getTextBounds(data.toString(), 0, data.toString().length, textBounds)
    }

    val xStart = padding / 2
    val xEnd = size.width - padding
    val nearestCenterIndex = if (count % 2 == 0) count / 2 else (count - 1) / 2
    val xMiddle = nearestCenterIndex * (size.width - padding) / (count - 1)

    val y = size.height + padding
    val textCount = data.toString().length.coerceAtLeast(minLabelCount)

    drawContext.canvas.nativeCanvas.drawText(
        data.first().toString().take(textCount),
        xStart,
        y,
        textPaint
    )
    drawContext.canvas.nativeCanvas.drawText(
        data.last().toString().take(textCount),
        xEnd,
        y,
        textPaint
    )
    drawContext.canvas.nativeCanvas.drawText(
        data[nearestCenterIndex].toString().take(textCount),
        xMiddle,
        y,
        textPaint
    )
}

fun DrawScope.drawYAxisLabels(
    values: List<Float>,
    spacing: Float,
    textColor: Color = Color.Black,
) {
    val maxLabelCount = 4
    val maxLabelValue = values.maxOrNull() ?: return
    val minLabelValue = values.minOrNull() ?: return
    val labelRange = maxLabelValue - minLabelValue

    val textPaint = Paint().apply {
        color = textColor.toArgb()
        textSize = size.width / 30
        textAlign = Paint.Align.CENTER
    }

    val labelSpacing = size.height / (maxLabelCount - 1)

    repeat(maxLabelCount) { i ->
        val y = size.height - (i * labelSpacing)
        val x = 0F.minus(spacing)
        val labelValue = minLabelValue + ((i * labelRange) / (maxLabelCount - 1))

        val text = if (labelValue.toString().length > 4) {
            labelValue.toString().take(4)
        } else {
            labelValue.toString()
        }

        drawContext.canvas.nativeCanvas.drawText(
            text,
            x,
            y,
            textPaint
        )
    }
}
