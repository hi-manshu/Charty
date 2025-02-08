package com.himanshoe.charty.point.model

/**
 * Data class representing a data point in a point chart.
 *
 * @property yValue The y-axis value of the data point.
 * @property xValue The x-axis value of the data point, which can be of any type.
 */
data class PointData(
    val yValue: Float,
    val xValue: Any,
)
