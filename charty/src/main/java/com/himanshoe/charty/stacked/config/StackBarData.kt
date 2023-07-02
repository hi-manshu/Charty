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
 * Represents the data for a stack bar chart.
 *
 * @param label The label associated with the data.
 * @param dataPoints The list of data points for each stack.
 * @param colors The list of colors for each stack (default is transparent for each stack).
 */
@Immutable
data class StackBarData(
    val label: String,
    val dataPoints: List<Float>,
    val colors: List<Color> = List(dataPoints.count()) { Color.Transparent }
)
