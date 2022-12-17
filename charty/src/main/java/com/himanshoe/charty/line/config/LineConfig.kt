package com.himanshoe.charty.line.config

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class LineConfig(
    val hasSmoothCurve: Boolean = false,
    val hasDotMarker: Boolean = false,
    val strokeSize: Dp = 5.dp
)

internal object LineConfigDefaults {

    fun lineConfigDefaults() = LineConfig(
        hasSmoothCurve = true,
        hasDotMarker = true,
        strokeSize = 5.dp
    )
}
