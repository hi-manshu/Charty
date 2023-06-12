package com.himanshoe.charty.circle.config

import com.himanshoe.charty.common.config.StartAngle

data class CircleChartConfig(
    val startAngle: StartAngle = StartAngle.Zero,
    val maxValue: Float?,
    val showLabel: Boolean
)
