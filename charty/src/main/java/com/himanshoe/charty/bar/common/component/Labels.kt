package com.himanshoe.charty.bar.common.component

import android.graphics.Paint
import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import com.himanshoe.charty.bar.model.BarData

internal fun DrawScope.drawBarLabel(
    data: BarData,
    barWidth: Float,
    barHeight: Float,
    topLeft: Offset
) {
    drawIntoCanvas {
        it.nativeCanvas.apply {
            drawText(
                data.xValue.toString(),
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
