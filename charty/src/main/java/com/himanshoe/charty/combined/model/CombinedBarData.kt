package com.himanshoe.charty.combined.model

data class CombinedBarData(val xValue: Any, val yBarValue: Float, val yLineValue: Float)

internal fun List<CombinedBarData>.maxYValue() = maxOf {
    it.yBarValue
}
