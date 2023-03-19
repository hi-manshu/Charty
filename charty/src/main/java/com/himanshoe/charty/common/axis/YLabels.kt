package com.himanshoe.charty.common.axis

import androidx.compose.ui.graphics.Color

data class YLabels(
    val fontColor: Color = Color.Black,
    val fontSize: Float = 12f,
    val rangeAdjustment: Multiplier = Multiplier(0f),
    val maxValueAdjustment: Multiplier = Multiplier(0f),
    val minValueAdjustment: Multiplier = Multiplier(0f),
    val isBaseZero: Boolean = false,
    val breaks: Int = 5
)

internal object YLabelsDefaults {
    fun yLabelsDefaults() = YLabels()
}
