package com.himanshoe.charty.line.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object LineChartDefaults {

    fun defaultConfig() = LineConfig(
        hasSmoothCurve = true,
        hasDotMarker = true,
        strokeSize = 5.dp
    )

    fun defaultColor() = LineChartColors(
        listOf(
            Color(0xffed625d),
            Color(0xfff79f88)
        ), listOf(
            Color(0xffed625d),
            Color(0xfff79f88)
        ), listOf(
            Color(0xffed625d),
            Color(0xfff79f88)
        )
    )
}