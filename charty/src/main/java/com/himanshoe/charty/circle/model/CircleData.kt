package com.himanshoe.charty.circle.model

import androidx.compose.ui.graphics.Color

data class CircleData(val xValue: Any, val yValue: Float, val color: Color? = null)

fun List<CircleData>.maxYValue() = maxOf {
    it.yValue
}
