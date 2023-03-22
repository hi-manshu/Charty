package com.himanshoe.charty.common.label

import android.graphics.Paint.Align
import androidx.compose.ui.graphics.Color

data class XLabels(
    val fontColor: Color = Color.Black,
    val fontSize: Float = 24f,
    val rangeAdjustment: Multiplier = Multiplier(.1f),
    val breaks: Int = 5,
    val yOffset: Float = 50f,
    val textAlignment: Align = Align.CENTER,
    val rotation: Float = 0f,
    val lineAlpha: Float = .1f,
    val showLines: Boolean = false
)

internal object XLabelsDefaults {
    fun xLabelsDefaults() = XLabels()
}
