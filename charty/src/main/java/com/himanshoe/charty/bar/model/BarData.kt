package com.himanshoe.charty.bar.model

data class BarData(val xValue: Float, val yValue: Float)

fun List<BarData>.maxYValue() = maxOf {
    it.yValue
}
