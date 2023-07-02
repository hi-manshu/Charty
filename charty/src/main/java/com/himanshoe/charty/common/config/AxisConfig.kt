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
 * Configuration for the axes and grid lines in a chart.
 *
 * @param showAxes Whether to show the axes.
 * @param showGridLines Whether to show the grid lines.
 * @param showGridLabel Whether to show the grid labels.
 * @param axisStroke The stroke width of the axes.
 * @param minLabelCount The minimum number of labels to display.
 * @param axisColor The color of the axes.
 * @param gridColor The color of the grid lines. Defaults to a slightly transparent version of the axis color.
 */
@Immutable
data class AxisConfig(
    val showAxes: Boolean,
    val showGridLines: Boolean,
    val showGridLabel: Boolean,
    val axisStroke: Float,
    val minLabelCount: Int,
    val axisColor: Color,
    val gridColor: Color = axisColor.copy(alpha = 0.5F),
)
