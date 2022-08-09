package com.himanshoe.charty.bar.config

data class BarConfig(
    val hasRoundedCorner: Boolean = false
)

internal object BarConfigDefaults {

    fun barConfigDimesDefaults() = BarConfig(
        hasRoundedCorner = true
    )
}
