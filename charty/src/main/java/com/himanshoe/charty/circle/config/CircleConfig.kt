package com.himanshoe.charty.circle.config

data class CircleConfig(
    val startPosition: StartPosition = StartPosition.Top,
    val maxValue: Float?
)


internal object CircleConfigDefaults {

    fun circleConfigDefaults() = CircleConfig(
        startPosition = StartPosition.Custom(30F),
        maxValue = null,
    )
}
