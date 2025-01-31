package com.himanshoe.charty.common

import androidx.compose.ui.graphics.Color


sealed class ChartColor(open val value: List<Color> = emptyList()) {
    data class Solid(val color: Color) : ChartColor(listOf(color, color))
    data class Gradient(override val value: List<Color>) : ChartColor(value)
}