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

@Immutable
data class CircleChartConfig(
    val startAngle: StartAngle = StartAngle.Zero,
    val maxValue: Float?,
    val showLabel: Boolean
)
