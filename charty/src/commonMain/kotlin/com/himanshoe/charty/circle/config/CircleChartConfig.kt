/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.circle.config

import androidx.compose.runtime.Immutable
import com.himanshoe.charty.common.config.StartAngle

/**
 * Configuration options for a circle chart.
 *
 * @param startAngle The start angle of the chart.
 * @param maxValue The maximum value of the chart.
 * @param showLabel Whether to show labels on the chart.
 */
@Immutable
data class CircleChartConfig(
    val startAngle: StartAngle = StartAngle.Zero,
    val maxValue: Float?,
    val showLabel: Boolean
)
