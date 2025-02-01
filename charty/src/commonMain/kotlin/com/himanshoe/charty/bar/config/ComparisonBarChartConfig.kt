package com.himanshoe.charty.bar.config

data class ComparisonBarChartConfig(
    val showAxisLines: Boolean,
    val showGridLines: Boolean,
    val drawNegativeValueChart: Boolean,
    val showCurvedBar: Boolean,
) {
    companion object {
        fun default() = ComparisonBarChartConfig(
            showAxisLines = true,
            showGridLines = true,
            drawNegativeValueChart = true,
            showCurvedBar = true,
        )
    }
}
