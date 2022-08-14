package com.himanshoe.charty.line.config

import androidx.compose.ui.graphics.Color

data class CurveLineConfig(
    val hasDotMarker: Boolean,
    val dotColor: Color,
)

internal object CurveLineConfigDefaults {

    fun curveLineConfigDefaults() = CurveLineConfig(
        hasDotMarker = true,
        dotColor = Color.Green
    )
}
