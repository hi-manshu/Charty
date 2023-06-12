package com.himanshoe.charty.common

import androidx.compose.runtime.Immutable

interface ChartData {
    val xValue: Any
    val yValue: Float
    val chartString: String
}

@Immutable
data class ChartDataCollection(val data: List<ChartData>)

fun ChartDataCollection.maxYValue() = data.maxOf { it.yValue }

fun ChartDataCollection.minYValue() = data.minOf { it.yValue }
