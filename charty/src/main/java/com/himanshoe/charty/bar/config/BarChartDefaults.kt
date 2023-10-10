/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.bar.config

import androidx.compose.ui.graphics.Color
import com.himanshoe.charty.bar.config.BarChartColors

/**
 * Default configurations and colors for a bar chart.
 */
object BarChartDefaults {

    /**
     * Returns the default colors for a bar chart.
     *
     * @return The default area chart colors.
     */
    fun defaultColor() = BarChartColors(
        backgroundColors = listOf(
            Color.White,
            Color.White,
        )
    )
}