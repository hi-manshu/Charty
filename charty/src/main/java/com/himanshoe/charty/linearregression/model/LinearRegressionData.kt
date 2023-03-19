package com.himanshoe.charty.linearregression.model

data class LinearRegressionData(val xValue: Float, val yPointValue: Float, val yLineValue: Float)

internal fun List<LinearRegressionData>.maxYValue() = maxOf {
    if (it.yPointValue > it.yLineValue) it.yPointValue else it.yLineValue
}
internal fun List<LinearRegressionData>.minYValue() = minOf {
    if (it.yPointValue < it.yLineValue) it.yPointValue else it.yLineValue
}
internal fun List<LinearRegressionData>.maxXValue() = maxOf { it.xValue }
internal fun List<LinearRegressionData>.minXValue() = minOf { it.xValue }
