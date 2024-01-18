package com.himanshoe.charty.radar.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Represents the colors used in a radar chart.
 *
 * @property lineColor The colors of the lines in the chart.
 * @property dotColor The colors of the dots in the chart.
 * @property fillColor The fill color of the polygon in the chart.
 */
@Immutable
data class RadarChartColors(
    val lineColor: List<Color>,
    val dotColor: List<Color>,
    val fillColor: List<Color> = lineColor.map { it.copy(alpha = 0.5F) }
)
