package com.himanshoe.charty.line.config

data class LineConfig(
    val hasSmoothCurve: Boolean = true,
    val hasDotMarker: Boolean
)

internal object LineConfigDefaults {

    fun lineConfigDefaults() = LineConfig(
        hasSmoothCurve = true,
        hasDotMarker = true
    )
}
