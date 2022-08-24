package com.himanshoe.charty.combined.config

import androidx.compose.ui.graphics.Color

data class CombinedBarConfig(
    val hasRoundedCorner: Boolean = false,
    val hasSmoothCurve: Boolean = false,
    val hasDotMarker: Boolean = false,
    val hasLineLabel: Boolean = false,
    val lineLabelColor: Pair<Color, Color> = Color.White to Color.Black
)

internal object CombinedBarConfigDefaults {

    fun barConfigDimesDefaults() = CombinedBarConfig(
        hasRoundedCorner = false,
        hasSmoothCurve = true,
        hasDotMarker = true,
        hasLineLabel = true,
        lineLabelColor = Color.White to Color.Blue
    )
}
