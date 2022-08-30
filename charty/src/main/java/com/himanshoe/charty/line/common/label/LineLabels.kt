package com.himanshoe.charty.line.common

import android.graphics.Paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import com.himanshoe.charty.line.model.LineData

internal fun DrawScope.drawLineLabel(
    data: LineData,
    lineWidth: Float,
    lineHeight: Float,
    topLeft: Offset,
    count: Int
) {
    val divisibleFactor = if (count > 10) count else 1
    val textSizeFactor = if (count > 10) 3 else 30
    drawIntoCanvas {
        it.nativeCanvas.apply {
            drawText(
                data.xValue.toString(),
                topLeft.x.plus(lineWidth.div(2)),
                topLeft.y.plus(lineHeight.plus(lineWidth.div(2))),
                Paint().apply {
                    textSize = size.width.div(textSizeFactor).div(divisibleFactor)
                    textAlign = Paint.Align.CENTER
                }
            )
        }
    }
}
