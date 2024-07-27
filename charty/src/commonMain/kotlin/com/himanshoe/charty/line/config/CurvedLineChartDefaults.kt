/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.line.config

import androidx.compose.ui.graphics.Color

/**
 * Default configuration values for a curved line chart.
 */
object CurvedLineChartDefaults {

    /**
     * Returns the default colors for the curved line chart.
     *
     * @return The default colors for the curved line chart.
     */
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
