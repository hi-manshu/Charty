package com.himanshoe.charty.line.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class CurveLineConfig(
    val hasDotMarker: Boolean,
    val dotColor: Color = Color.Transparent,
    val strokeSize: Dp = 5.dp
)

internal object CurveLineConfigDefaults {

    fun curveLineConfigDefaults() = CurveLineConfig(
        hasDotMarker = true,
        dotColor = Color.Green,
        strokeSize = 5.dp
    )
}
