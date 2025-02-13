package com.himanshoe.charty.bar.config

import androidx.compose.ui.geometry.CornerRadius

/**
 * Configuration class for bar chart settings.
 *
 * @property showAxisLines A boolean indicating whether to show axis lines.
 * @property showGridLines A boolean indicating whether to show grid lines.
 * @property drawNegativeValueChart A boolean indicating whether to draw a chart with negative values.
 * @property showCurvedBar A boolean indicating whether to show bars with curved corners.
 * @property minimumBarCount The minimum number of bars to display in the chart.
 */
data class BarChartConfig(
    val showAxisLines: Boolean,
    val showGridLines: Boolean,
    val drawNegativeValueChart: Boolean,
    val showCurvedBar: Boolean,
    val minimumBarCount: Int,
    val cornerRadius: CornerRadius?
) {
    companion object {
        /**
         * Provides a default configuration for bar chart settings.
         *
         * @return A `BarChartConfig` object with default settings.
         */
        fun default() = BarChartConfig(
            showAxisLines = true,
            showGridLines = true,
            drawNegativeValueChart = true,
            showCurvedBar = false,
            minimumBarCount = 7,
            cornerRadius = null
        )
    }
}
