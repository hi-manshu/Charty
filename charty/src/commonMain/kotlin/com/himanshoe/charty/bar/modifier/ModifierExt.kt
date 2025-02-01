package com.himanshoe.charty.bar.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.sp
import com.himanshoe.charty.common.LabelConfig

fun Modifier.drawAxesAndGridLines(
    maxValue: Float,
    step: Float,
    textMeasurer: TextMeasurer,
    labelConfig: LabelConfig,
    showAxisLines: Boolean,
    showGridLines: Boolean
) = this.drawWithCache {

    val canvasWidth = size.width
    val canvasHeight = size.height
    onDrawBehind {
        if (showAxisLines) {
            // Draw axes
            drawLine(
                color = Color.Black,
                start = Offset(0f, canvasHeight),
                end = Offset(canvasWidth, canvasHeight),
                strokeWidth = 2f
            )
            drawLine(
                color = Color.Black,
                start = Offset(0f, 0f),
                end = Offset(0f, canvasHeight),
                strokeWidth = 2f
            )
        }
        if (showGridLines) {

            // Draw grid lines
            for (i in 0..4) {
                val yValue = step * i
                val yOffset = canvasHeight - (yValue / maxValue) * canvasHeight
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, yOffset),
                    end = Offset(canvasWidth, yOffset),
                    strokeWidth = 1f
                )
            }
        }

        // Draw y-axis labels
        if (labelConfig.showYLabel) {
            for (i in 0..4) {
                val yValue = step * i
                val yOffset = canvasHeight - (yValue / maxValue) * canvasHeight
                val textLayoutResult = textMeasurer.measure(
                    text = yValue.toString(),
                    style = TextStyle(fontSize = 12.sp)
                )
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x = (-textLayoutResult.size.width - 4).toFloat(),
                        y = yOffset - textLayoutResult.size.height / 2 + 4 // Adjusted yOffset
                    ),
                    brush = SolidColor(Color.Black)
                )
            }
        }
    }
}