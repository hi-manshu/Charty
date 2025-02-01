package com.himanshoe.charty.bar.model

import com.himanshoe.charty.common.ChartColor

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