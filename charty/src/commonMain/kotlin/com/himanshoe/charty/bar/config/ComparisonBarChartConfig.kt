package com.himanshoe.charty.bar.config

/**
 * Configuration class for comparison bar chart settings.
 *
 * @property showAxisLines A boolean indicating whether to show axis lines.
 * @property showGridLines A boolean indicating whether to show grid lines.
 * @property drawNegativeValueChart A boolean indicating whether to draw a chart with negative values.
 * @property showCurvedBar A boolean indicating whether to show bars with curved corners.
 */
data class ComparisonBarChartConfig(
    val showAxisLines: Boolean,
    val showGridLines: Boolean,
    val drawNegativeValueChart: Boolean,
    val showCurvedBar: Boolean,
) {
    companion object {
        /**
         * Provides a default configuration for comparison bar chart settings.
         *
         * @return A `ComparisonBarChartConfig` object with default settings.
         */
        fun default() = ComparisonBarChartConfig(
            showAxisLines = true,
            showGridLines = true,
            drawNegativeValueChart = true,
            showCurvedBar = true,
        )
    }
}