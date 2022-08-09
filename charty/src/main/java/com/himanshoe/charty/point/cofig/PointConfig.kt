package com.himanshoe.charty.point.cofig

data class PointConfig(
    val pointType: PointType,
)

internal object PointConfigDefaults {

    fun pointConfigDefaults() = PointConfig(
        pointType = PointType.Stroke
    )
}

sealed interface PointType {
    object Fill : PointType
    object Stroke : PointType
}
