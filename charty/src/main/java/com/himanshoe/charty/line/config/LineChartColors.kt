/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.line.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Represents the colors used in a line chart.
 *
 * @property lineColor The colors of the lines in the chart.
 * @property dotColor The colors of the dots in the chart.
 * @property backgroundColors The background colors of the chart.
 */
@Immutable
data class LineChartColors(
    val lineColor: List<Color> = emptyList(),
    val dotColor: List<Color> = emptyList(),
    val backgroundColors: List<Color> = emptyList(),
)

/**
 * Represents the colors used in a curved line chart.
 *
 * @property dotColor The colors of the dots in the chart.
 * @property backgroundColors The background colors of the chart.
 * @property contentColor The colors of the content (lines) in the chart.
 */
@Immutable
data class CurvedLineChartColors(
    val dotColor: List<Color> = emptyList(),
    val backgroundColors: List<Color> = emptyList(),
    val contentColor: List<Color> = emptyList(),
)
