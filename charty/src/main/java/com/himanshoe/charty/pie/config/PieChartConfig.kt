/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.pie.config

import androidx.compose.runtime.Immutable
import com.himanshoe.charty.common.config.StartAngle

/**
 * Immutable data class representing the configuration options for a pie chart.
 *
 * @param donut Indicates whether the pie chart should be rendered as a donut.
 * @param showLabel Indicates whether labels should be shown for each pie slice.
 * @param startAngle The starting angle of the pie chart.
 */
@Immutable
data class PieChartConfig(
    val donut: Boolean,
    val showLabel: Boolean,
    val startAngle: StartAngle = StartAngle.Zero
)
