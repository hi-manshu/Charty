/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.group.config

import androidx.compose.ui.graphics.Color

/**
 * Default configurations and colors for a group bar chart.
 */
object GroupBarChartDefaults {

    /**
     * Returns the default colors for a group bar chart.
     *
     * @return The default group bar chart colors.
     */
    fun defaultColor() = GroupBarChartColors(
        backgroundColors = listOf(
            Color.White,
            Color.White,
        )
    )
}