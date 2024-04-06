package com.himanshoe.charty.radar.config

import androidx.compose.runtime.Immutable

/**
 * Configuration options for a data polygon in a radar chart.
 *
 * @property hasDotMarker Whether the polygon should have a dot marker at each data point.
 * @property strokeSize The size of the line stroke.
 * @property fillPolygon Whether the polygon should be filled with a color.
 */
@Immutable
data class RadarConfig(
    val hasDotMarker: Boolean,
    val strokeSize: Float,
    val fillPolygon: Boolean,
)
