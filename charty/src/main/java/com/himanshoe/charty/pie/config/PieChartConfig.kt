package com.himanshoe.charty.pie.config

import androidx.compose.runtime.Immutable
import com.himanshoe.charty.common.config.StartAngle

@Immutable
data class PieChartConfig(
    val donut: Boolean,
    val showLabel: Boolean,
    val startAngle: StartAngle = StartAngle.Zero
)
