/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.common

import androidx.compose.runtime.Immutable

/*
 * Represents a single data point in a chart.
 * - xValue: The value on the x-axis.
 * - yValue: The value on the y-axis.
 * - chartString: A string representation of the data point.
 */
interface ChartData {
    val xValue: Any
    val yValue: Float
    val chartString: String
}

/*
 * Represents a collection of ChartData objects.
 * - data: The list of ChartData objects.
 */
@Immutable
data class ChartDataCollection(val data: List<ChartData>)

/*
 * Extension function to convert a List of ChartData objects to a ChartDataCollection.
 */
fun List<ChartData>.toChartDataCollection() = ChartDataCollection(this)

/*
 * Returns the maximum y-value among all the data points in the ChartDataCollection.
 */
fun ChartDataCollection.maxYValue() = data.maxOf { it.yValue }

/*
 * Returns the minimum y-value among all the data points in the ChartDataCollection.
 */
fun ChartDataCollection.minYValue() = data.minOf { it.yValue }

/*
 * Represents a collection of items for Compose.
 * - data: The list of items.
 */
@Immutable
data class ComposeList<T>(val data: List<T>)

/*
 * Extension function to convert a List to a ComposeList.
 */
fun <T> List<T>.toComposeList() = ComposeList(this)
