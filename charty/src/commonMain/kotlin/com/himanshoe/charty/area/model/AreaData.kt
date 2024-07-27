/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.area.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Data class representing an area on the AreaChart.
 *
 * @param xValue The x-value of the area. It can be of any type (e.g., String, Int, Float, etc.).
 * @param points The list of y-values that define the area's shape. Each y-value corresponds to a specific data point.
 * @param color The color of the area.
 */
@Immutable
data class AreaData(
    val xValue: Any,
    val points: List<Float>,
    val color: Color
)
