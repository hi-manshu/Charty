package com.himanshoe.charty.line.config

import androidx.compose.runtime.Immutable

@Immutable
data class LineConfig(
    val hasSmoothCurve: Boolean,
    val hasDotMarker: Boolean,
    val strokeSize: Float
)
