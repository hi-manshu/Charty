package com.himanshoe.charty.line.model

/**
 * Data class representing a data point in a line chart.
 *
 * @property yValue The y-axis value of the data point.
 * @property xValue The x-axis value of the data point, which can be of any type.
 */
data class LineData(
    val yValue: Float,
    val xValue: Any,
)
