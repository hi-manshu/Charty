package com.himanshoe.charty.combined.common.component

import android.graphics.Paint
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import com.himanshoe.charty.combined.model.CombinedBarData

internal fun DrawScope.drawCombinedBarLabel(
    combinedBarData: CombinedBarData,
    barWidth: Float,
    barHeight: Float,
    topLeft: Offset
) {
    drawIntoCanvas {
        it.nativeCanvas.apply {
            drawText(
                combinedBarData.xValue.toString(),
                topLeft.x.plus(barWidth.div(2)),
                topLeft.y.plus(barHeight.plus(barWidth.div(2))),
                Paint().apply {
                    textSize = size.width.div(30)
                    textAlign = Paint.Align.CENTER
                }
            )
        }
    }
}

internal fun DrawScope.drawLineLabels(
    offset: Offset,
    combinedBarData: CombinedBarData,
    lineLabelColor: Pair<Color, Color>,
) {
    val textSp = size.width.div(25)
    drawIntoCanvas {
        drawRoundRect(
            cornerRadius = CornerRadius(50F),
            color = lineLabelColor.first,
            topLeft = Offset(
                offset.x.minus(textSp.times(1.3F)),
                offset.y.minus(textSp.times(2.1F))
            ),
            size = Size(
                textSp.times(2.7F),
                textSp.times(1.5F)
            )
        )
        it.nativeCanvas.apply {
            drawText(
                combinedBarData.yLineValue.toString(),
                offset.x,
                offset.y.minus(textSp),
                Paint().apply {
                    color = lineLabelColor.second.toArgb()
                    textSize = textSp
                    textAlign = Paint.Align.CENTER
                }
            )
        }
    }
}
