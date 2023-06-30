/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.common

import androidx.compose.runtime.Immutable

interface ChartData {
    val xValue: Any
    val yValue: Float
    val chartString: String
}

@Immutable
data class ChartDataCollection(val data: List<ChartData>)

fun List<ChartData>.toChartDataCollection() = ChartDataCollection(this)

fun ChartDataCollection.maxYValue() = data.maxOf { it.yValue }

fun ChartDataCollection.minYValue() = data.minOf { it.yValue }

@Immutable
data class ComposeList<T>(val data: List<T>)

fun <T> List<T>.toComposeList() = ComposeList(this)
