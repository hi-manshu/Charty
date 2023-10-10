/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.area.config

import androidx.compose.ui.graphics.Color

/**
 * Default configurations and colors for a area chart.
 */
object AreaChartDefaults {

    /**
     * Returns the default colors for a area chart.
     *
     * @return The default area chart colors.
     */
    fun defaultColor() = AreaChartColors(
        backgroundColors = listOf(
            Color.White,
            Color.White,
        )
    )
}