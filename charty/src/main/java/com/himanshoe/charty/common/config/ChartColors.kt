/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.common.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Colors used in a chart.
 *
 * @param contentColor The color(s) for the content of the chart.
 * @param backgroundColors The background color(s) of the chart.
 */
@Immutable
data class ChartColors(
    val contentColor: List<Color> = emptyList(),
    val backgroundColors: List<Color> = emptyList(),
)
