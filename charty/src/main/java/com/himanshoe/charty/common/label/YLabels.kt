package com.himanshoe.charty.common.label

import android.graphics.Paint.Align
import androidx.compose.ui.graphics.Color

data class YLabels(
    val fontColor: Color = Color.Black,
    val fontSize: Float = 24f,
    val rangeAdjustment: Multiplier = Multiplier(0f),
    val maxValueAdjustment: Multiplier = Multiplier(0f),
    val minValueAdjustment: Multiplier = Multiplier(0f),
    val isBaseZero: Boolean = false,
    val breaks: Int = 5,
    val xOffset: Float = 25f,
    val yOffset: Float = 0f,
    val textAlignment: Align = Align.CENTER,
    val rotation: Float = 0f,
    val lineAlpha: Float = .1f,
    val lineStartPadding: Float = 25f
)

internal object YLabelsDefaults {
    fun yLabelsDefaults() = YLabels()
}
