/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.area.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Represents the colors used in a area chart.
 *
 * @property backgroundColors The background colors of the chart.
 */
@Immutable
data class AreaChartColors(
    val backgroundColors: List<Color> = emptyList(),
)
