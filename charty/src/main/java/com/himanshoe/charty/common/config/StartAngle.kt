package com.himanshoe.charty.common.config

import androidx.compose.runtime.Immutable


@Immutable
sealed class StartAngle(open val angle: Float) {
    object Zero : StartAngle(0F)
    object StraightAngle : StartAngle(180F)
    object ReflexAngle : StartAngle(270F)
    object RightAngle : StartAngle(90F)
    data class CustomAngle(override val angle: Float) : StartAngle(angle)
}
