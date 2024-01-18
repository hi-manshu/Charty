package com.himanshoe.charty.radar.config

import androidx.compose.ui.graphics.Color

/**
 * Default configurations and colors for a radar chart.
 */
object RadarChartDefaults {

    /**
     * Returns the default configuration for a radar chart.
     *
     * @return The default radar chart configuration.
     */
    fun defaultConfig(): RadarConfig =
        RadarConfig(
            hasDotMarker = true,
            strokeSize = 5F,
            fillPolygon = true,
        )

    /**
     * Returns the default colors for a radar chart.
     *
     * @return The default radar chart colors.
     */
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