/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.common.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

/**
 * Draws X-axis labels for a single data point.
 *
 * @param data The data value for the label.
 * @param center The center position of the label.
 * @param count The total count of data points.
 * @param padding The padding value.
 * @param minLabelCount The minimum label count.
 * @param textColor The color of the text label.
 */
internal fun DrawScope.drawXAxisLabels(
    data: Any,
    center: Offset,
    count: Int,
    padding: Float,
    minLabelCount: Int,
    textMeasurer: TextMeasurer,
    textColor: Color = Color.Black
) {
    val divisibleFactor = if (count > 10) count else 1
    val textSizeFactor = if (count > 10) 9 else 90

    val textStyle = TextStyle(
        color = textColor,
        textAlign = TextAlign.Center,
        fontSize = (size.width / textSizeFactor / divisibleFactor).sp
    )

    val textMeasurement = textMeasurer.measure(
        text = data.toString().take(minLabelCount),
        style = textStyle
    )

    drawText(
        textMeasurer = textMeasurer,
        topLeft = Offset(center.x - (textMeasurement.size.width / 2), (size.height + padding / 2)),
        style = textStyle,
        text = data.toString().take(minLabelCount),
        size = Size(
            width = textMeasurement.size.width.toFloat(),
            height = textMeasurement.size.width.toFloat()
        )
    )
}

/**
 * Draws X-axis labels for a list of data points.
 *
 * @param data The list of data points.
 * @param count The total count of data points.
 * @param padding The padding value.
 * @param minLabelCount The minimum label count.
 * @param textColor The color of the text labels.
 */
internal fun DrawScope.drawXAxisLabels(
    data: List<Any>,
    count: Int,
    padding: Float,
    minLabelCount: Int,
    textMeasurer: TextMeasurer,
    textColor: Color = Color.Black,
) {

    val xStart = padding / 2
    val xEnd = size.width - padding
    val nearestCenterIndex = if (count % 2 == 0) count / 2 else (count - 1) / 2
    val xMiddle = nearestCenterIndex * (size.width - padding) / (count - 1)

    val y = size.height + padding
    val textCount = data.toString().length.coerceAtLeast(minLabelCount)

    val textStyle = TextStyle(
        color = textColor,
        textAlign = TextAlign.Center,
        fontSize = (size.width / 90).sp
    )

    val textStart = data.first().toString().take(textCount)
    val (startTextWidth, startTextHeight) = textMeasurer.measure(
        text = textStart,
        style = textStyle
    ).size

    drawText(
        textMeasurer = textMeasurer,
        style = textStyle,
        topLeft = Offset(
            xStart - (startTextWidth / 2),
            y - (startTextHeight / 2)
        ),
        text = textStart,
        size = Size(
            width = startTextWidth.toFloat(),
            height = startTextHeight.toFloat()
        )
    )

    val textEnd = data.last().toString().take(textCount)
    val (textEndWidth, textEndHeight) = textMeasurer.measure(
        text = textEnd,
        style = textStyle
    ).size

    drawText(
        textMeasurer = textMeasurer,
        style = textStyle,
        topLeft = Offset(
            xEnd - (textEndWidth / 2),
            y - (textEndHeight / 2)
        ),
        text = textEnd,
        size = Size(
            width = textEndWidth.toFloat(),
            height = textEndHeight.toFloat()
        )
    )

    val textMiddle = data[nearestCenterIndex].toString().take(textCount)
    val (textMiddleWidth, textMiddleHeight) = textMeasurer.measure(
        text = textMiddle,
        style = textStyle
    ).size

    drawText(
        textMeasurer = textMeasurer,
        style = textStyle,
        topLeft = Offset(
            xMiddle - (textMiddleWidth / 2),
            y - (textMiddleHeight / 2)
        ),
        text = textMiddle,
        size = Size(
            width = textMiddleWidth.toFloat(),
            height = textMiddleHeight.toFloat()
        )
    )
}

/**
 * Draws Y-axis labels for a list of values.
 *
 * @param values The list of values.
 * @param spacing The spacing between labels.
 * @param textColor The color of the text labels.
 */
fun DrawScope.drawYAxisLabels(
    values: List<Float>,
    spacing: Float,
    textMeasurer: TextMeasurer,
    textColor: Color = Color.Black,
) {
    val maxLabelCount = 4
    val maxLabelValue = values.maxOrNull() ?: return
    val minLabelValue = values.minOrNull() ?: return
    val labelRange = maxLabelValue - minLabelValue

    val textStyle = TextStyle(
        color = textColor,
        textAlign = TextAlign.Center,
        fontSize = (size.width / 80).sp
    )

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

        val textMeasurement = textMeasurer.measure(
            text = text,
            style = textStyle
        )

        val (textWidth, textHeight) = textMeasurement.size

        drawText(
            textMeasurer = textMeasurer,
            topLeft = Offset(
                x - (textWidth / 2),
                y - textMeasurement.lastBaseline
            ),
            text = text,
            style = textStyle,
            size = Size(
                width = textWidth.toFloat(),
                height = textHeight.toFloat()
            )
        )
    }
}
