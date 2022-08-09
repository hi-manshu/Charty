package com.himanshoe.charty.bar.model

data class BarData(val xValue: Any, val yValue: Float)

fun List<BarData>.maxYValue() = maxOf {
    it.yValue
}
