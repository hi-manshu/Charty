package com.himanshoe.charty.line.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.line.model.LineData
import com.himanshoe.charty.line.config.LineChartColorConfig
import com.himanshoe.charty.line.config.LineChartConfig


fun Modifier.drawAxesAndGridLines(
    lineData: List<LineData>,
    colorConfig: LineChartColorConfig,
    chartConfig: LineChartConfig,
    textMeasurer: TextMeasurer,
    labelConfig: LabelConfig,
    minValue: Float,
    yRange: Float,
) = this.drawBehind {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val xStep = canvasWidth / (lineData.size - 1)
    val yStep = canvasHeight / 4

    // Draw X and Y axis
    drawLine(
        brush = Brush.linearGradient(colorConfig.axisColor.value),
        start = Offset(0f, canvasHeight),
        end = Offset(canvasWidth, canvasHeight),
        strokeWidth = chartConfig.axisLineWidth
    )
    drawLine(
        brush = Brush.linearGradient(colorConfig.axisColor.value),
        start = Offset(0f, 0f),
        end = Offset(0f, canvasHeight),
        strokeWidth = chartConfig.axisLineWidth
    )

    // Draw parallel lines to X axis
    for (i in 1..4) {
        val yOffset = canvasHeight - i * yStep
        drawLine(
            brush = Brush.linearGradient(colorConfig.gridLineColor.value),
            start = Offset(0f, yOffset),
            pathEffect = chartConfig.gridLinePathEffect,
            end = Offset(canvasWidth, yOffset),
            strokeWidth = chartConfig.gridLineWidth
        )
    }
    // Draw labels
    if (labelConfig.showXLabel) {
        lineData.forEachIndexed { index, lineData ->
            val textLayoutResult = textMeasurer.measure(
                text = lineData.xValue.toString(),
                style = TextStyle(fontSize = (canvasWidth / 70).sp),
            )
            drawText(
                textLayoutResult = textLayoutResult, topLeft = Offset(
                    x = index * xStep - textLayoutResult.size.width / 2,
                    y = canvasHeight + 4.dp.toPx() // Position below the X-axis
                ), brush = SolidColor(labelConfig.textColor)
            )
        }
    }

    if (labelConfig.showYLabel) {
        (0..4).forEach { i ->
            val value = minValue + i * yRange / 4
            val textLayoutResult = textMeasurer.measure(
                text = value.toString(),
                style = TextStyle(fontSize = (canvasWidth / 70).sp),
            )
            drawText(
                textLayoutResult = textLayoutResult, topLeft = Offset(
                    x = -textLayoutResult.size.width - 8f,
                    y = canvasHeight - i * yStep - textLayoutResult.size.height / 2
                ), brush = SolidColor(labelConfig.textColor)
            )
        }
    }

}