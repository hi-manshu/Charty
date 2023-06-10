package com.himanshoe.charty.line.config

import androidx.compose.ui.graphics.Color

object CurvedLineChartDefaults {

    fun defaultColor() = CurvedLineChartColors(
        contentColor = listOf(
            Color(0xffed625d),
            Color(0xfff79f88)
        ),
        dotColor = listOf(
            Color(0xff50c0a8),
            Color(0xff7a57e3),
        ),
        backgroundColors = listOf(
            Color.White,
            Color.White,
        ),
    )
}