/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.stacked.config

import androidx.compose.ui.graphics.Color

/**
 * Default configurations and colors for a stacked bar chart.
 */
object StackedBarChartDefaults {

    /**
     * Returns the default colors for a stacked bar chart.
     *
     * @return The default stacked bar chart colors.
     */
    fun defaultColor() = StackedBarChartColors(
        backgroundColors = listOf(
            Color.White,
            Color.White,
        )
    )
}