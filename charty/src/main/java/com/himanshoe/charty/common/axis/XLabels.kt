package com.himanshoe.charty.common.axis

import androidx.compose.ui.graphics.Color

data class XLabels(
    val fontColor: Color = Color.Black,
    val fontSize: Float = 12f,
    val rangeAdjustment: Multiplier = Multiplier(0f),
    val breaks: Int = 5
)

internal object XLabelsDefaults {
    fun xLabelsDefaults() = XLabels()
}
