package com.himanshoe.charty.bar.model

import com.himanshoe.charty.common.ChartColor

/**
 * Data class representing a comparison bar in a bar chart.
 *
 * @property label The label for the comparison bar.
 * @property bars A list of y-axis values for the bars.
 * @property colors A list of colors for the bars.
 * @constructor Creates a ComparisonBarData instance and ensures the size of bars and colors lists are equal.
 */
data class ComparisonBarData(
    val label: String,
    val bars: List<Float>,
    val colors: List<ChartColor>
) {
    init {
        require(bars.size == colors.size) {
            "The size of the bars list must be equal to the size of the colors list."
        }
    }
}