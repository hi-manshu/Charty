package com.himanshoe.charty.horizontalbar.config

data class HorizontalBarConfig(
    val showLabels: Boolean,
    val startDirection: StartDirection
)

internal object HorizontalBarConfigDefaults {

    fun horizontalBarConfig() = HorizontalBarConfig(
        showLabels = true,
        startDirection = StartDirection.Right
    )
}

sealed interface StartDirection {
    object Left : StartDirection
    object Right : StartDirection
}
