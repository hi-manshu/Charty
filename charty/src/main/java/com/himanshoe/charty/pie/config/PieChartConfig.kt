package com.himanshoe.charty.pie.config

import androidx.compose.runtime.Immutable

@Immutable
data class PieChartConfig(
    val donut: Boolean,
    val showLabel: Boolean,
    val startAngle: StartAngle = StartAngle.Zero
)

@Immutable
sealed class StartAngle(open val angle: Float) {
    object Zero : StartAngle(0F)
    object StraightAngle : StartAngle(180F)
    object ReflexAngle : StartAngle(270F)
    object RightAngle : StartAngle(90F)
    data class CustomAngle(override val angle: Float) : StartAngle(angle)
}
