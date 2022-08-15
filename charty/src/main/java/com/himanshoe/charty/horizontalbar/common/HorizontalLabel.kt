package com.himanshoe.charty.horizontalbar.common

import android.graphics.Paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import com.himanshoe.charty.horizontalbar.model.HorizontalBarData

internal fun DrawScope.drawHorizontalBarLabel(
    horizontalBarData: HorizontalBarData,
    barHeight: Float,
    topLeft: Offset,
) {
    drawIntoCanvas {
        it.nativeCanvas.apply {
            drawText(
                horizontalBarData.yValue.toString().take(3),
                0F.minus(barHeight.div(3)),
                topLeft.y.plus(barHeight.div(2)),
                Paint().apply {
                    textSize = size.width.div(30)
                    textAlign = Paint.Align.CENTER
                }
            )
        }
    }
}
