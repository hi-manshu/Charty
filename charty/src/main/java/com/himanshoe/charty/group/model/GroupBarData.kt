/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.group.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Immutable data class representing a group bar chart data.
 *
 * @param label The label for the group bar.
 * @param dataPoints The list of data points for each bar in the group.
 * @param colors The list of colors for each bar in the group. Defaults to [Color.Transparent]
 *               if not provided explicitly.
 */
@Immutable
data class GroupBarData(
    val label: String,
    val dataPoints: List<Float>,
    val colors: List<Color> = List(dataPoints.count()) { Color.Transparent }
)
