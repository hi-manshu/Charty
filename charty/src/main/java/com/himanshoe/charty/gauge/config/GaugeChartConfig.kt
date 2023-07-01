/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.gauge.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class GaugeChartConfig(
    val placeHolderColor: Color,
    val primaryColor: Color,
    val strokeWidth: Float,
    val showNeedle: Boolean,
    val showIndicator: Boolean,
    val indicatorColor: Color,
    val indicatorWidth: Float,
)
