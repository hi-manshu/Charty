package com.himanshoe.charty.circle.model

import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.common.asGradientChartColor

/**
 * Data class representing a single slice of a circle chart.
 *
 * @property value The value of the pie chart slice.
 * @property color The color of the pie chart slice.
 * @property label The text label for the pie chart slice.
 */
data class CircleData(
    val value: Float,
    val color: ChartColor,
    val trackColor: ChartColor = color.value.map { it.copy(alpha = 0.5F) }.asGradientChartColor(),
    val label: String,
)
