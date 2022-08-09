package com.himanshoe.charty.bar.model

import com.himanshoe.charty.line.model.BarData

data class BarData(val xValue: Any, val yValue: Float)

fun List<BarData>.maxYValue() = maxOf {
    it.yValue
}
