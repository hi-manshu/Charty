package com.himanshoe.charty.common

import androidx.compose.ui.graphics.Color

/**
 * A sealed class representing different types of chart colors.
 *
 * @property value A list of colors used for the chart.
 */
sealed class ChartColor(open val value: List<Color> = emptyList()) {
    /**
     * A data class representing a solid color for the chart.
     *
     * @property color The solid color.
     */
    data class Solid(val color: Color) : ChartColor(listOf(color, color))

    /**
     * A data class representing a gradient color for the chart.
     *
     * @property value A list of colors used for the gradient.
     */
    data class Gradient(override val value: List<Color>) : ChartColor(value)
}

/**
 * Extension function to convert a Color to a Solid ChartColor.
 *
 * @return A Solid ChartColor with the given color.
 */
fun Color.asSolidChartColor() = ChartColor.Solid(this)
fun Color.asGradientChartColor() = ChartColor.Gradient(listOf(this, this))

/**
 * Extension function to convert a list of Colors to a Gradient ChartColor.
 *
 * @return A Gradient ChartColor with the given list of colors.
 */
fun List<Color>.asGradientChartColor() = ChartColor.Gradient(this)
