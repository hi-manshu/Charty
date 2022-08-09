package com.himanshoe.charty.point.model

data class PointData(val xValue: Any, val yValue: Float)

fun List<PointData>.maxYValue() = maxOf {
    it.yValue
}
