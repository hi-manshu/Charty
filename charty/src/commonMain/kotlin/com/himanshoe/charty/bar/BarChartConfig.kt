package com.himanshoe.charty.bar

data class BarChartConfig(
    val showAxisLines: Boolean,
    val showRangeLines: Boolean,
    val drawNegativeValueChart: Boolean,
    val showCurvedBar: Boolean,
    val minimumBarCount: Int,
) {
    companion object {
        fun default() =
            BarChartConfig(
                showAxisLines = true,
                showRangeLines = true,
                drawNegativeValueChart = true,
                showCurvedBar = true,
                minimumBarCount = 7,
            )
    }
}
