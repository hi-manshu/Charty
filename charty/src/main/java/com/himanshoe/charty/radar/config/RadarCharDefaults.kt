package com.himanshoe.charty.radar.config

import androidx.compose.ui.graphics.Color

object RadarCharDefaults {

    fun defaultConfig(): RadarConfig =
        RadarConfig(
            hasDotMarker = true,
            strokeSize = 5F,
        )

    fun defaultColor(): RadarChartColors =
        RadarChartColors(
            lineColor = listOf(
                Color(0xffed625d),
                Color(0xfff79f88)
            ),
            dotColor = listOf(
                Color(0xff50c0a8),
                Color(0xff7a57e3),
            ),
        )
}