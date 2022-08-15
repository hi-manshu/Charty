package com.himanshoe.charty.horizontalbar.model

data class HorizontalBarData(val xValue: Float, val yValue: Any)

internal fun List<HorizontalBarData>.maxXValue() = maxOf {
    it.xValue
}
