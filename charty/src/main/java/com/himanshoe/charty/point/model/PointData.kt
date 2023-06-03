package com.himanshoe.charty.point.model

import androidx.compose.runtime.Immutable
import com.himanshoe.charty.common.ChartData

@Immutable
data class Point(override val yValue: Float, override val xValue: Any) : ChartData {
    override val chartString: String
        get() = "Point Chart"
}

@Immutable
data class PointData(val points: List<Point>)

fun PointData.maxYValue() = points.maxOf { it.yValue }

fun PointData.minYValue() = points.minOf { it.yValue }
