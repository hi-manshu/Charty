package com.himanshoe.charty.line.model

import com.himanshoe.charty.line.config.LineChartColorConfig

/**
 * Data class representing multiple lines of data in a line chart.
 *
 * @property data A list of LineData objects representing the data points for the lines.
 * @property colorConfig The color configuration for the lines in the chart.
 */
data class MultiLineData(
    val data: List<LineData>,
    val colorConfig: LineChartColorConfig
)
