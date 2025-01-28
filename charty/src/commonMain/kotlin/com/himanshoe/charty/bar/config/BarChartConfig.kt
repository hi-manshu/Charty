package com.himanshoe.charty.bar.config

data class BarChartConfig(
    val showAxisLines: Boolean,
    val showGridLines: Boolean,
    val drawNegativeValueChart: Boolean,
    val showCurvedBar: Boolean,
    val minimumBarCount: Int,
) {
    companion object {
        fun default() =
            BarChartConfig(
                showAxisLines = true,
                showGridLines = true,
                drawNegativeValueChart = true,
                showCurvedBar = false,
                minimumBarCount = 7,
            )
    }
}
