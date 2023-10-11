/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.stacked.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Represents the colors used in a stacked bar chart.
 *
 * @property backgroundColors The background colors of the chart.
 */
@Immutable
data class StackedBarChartColors(
    val backgroundColors: List<Color> = emptyList(),
)
