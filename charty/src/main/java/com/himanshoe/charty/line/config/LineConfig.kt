package com.himanshoe.charty.line.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

@Immutable
data class LineConfig(
    val hasSmoothCurve: Boolean,
    val hasDotMarker: Boolean,
    val strokeSize: Float
)