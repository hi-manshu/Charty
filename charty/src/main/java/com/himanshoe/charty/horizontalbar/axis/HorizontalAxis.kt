package com.himanshoe.charty.horizontalbar.axis

import android.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import java.text.DecimalFormat

internal fun DrawScope.horizontalYAxis(
    axisConfig: HorizontalAxisConfig,
    maxValue: Float,
    startAngle: Float
) {
    val xScaleFactor = size.width.div(4)
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 20f), 0f)
    val axisScaleFactor = maxValue.div(4)
    val graphHeight = size.height

    val list: List<String> = (0..5).toList().map {
        getLabelText(axisScaleFactor.times(it))
    }

    list.forEachIndexed { index, text ->
        if (axisConfig.showUnitLabels) {
            drawIntoCanvas {
                it.nativeCanvas.apply {
                    drawText(
                        text,
                        xScaleFactor.times(index),
                        graphHeight.plus(50F),
                        Paint().apply {
                            textSize = size.width.div(30)
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }
        }
    }
}

private fun getLabelText(value: Float) = DecimalFormat("#.##").format(value).toString()
