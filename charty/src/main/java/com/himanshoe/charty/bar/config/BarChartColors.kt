/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.bar.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Represents the colors used in a bar chart.
 *
 * @property backgroundColors The background colors of the chart.
 */
@Immutable
data class BarChartColors(
    val backgroundColors: List<Color> = emptyList(),
)
