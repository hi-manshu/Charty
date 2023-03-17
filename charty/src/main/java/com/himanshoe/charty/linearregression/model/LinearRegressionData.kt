package com.himanshoe.charty.linearregression.model

data class LinearRegressionData(val xValue: Any, val yPointValue: Float, val yLineValue: Float)

internal fun List<LinearRegressionData>.maxYValue() = maxOf {
    if (it.yPointValue > it.yLineValue) it.yPointValue else it.yLineValue
}
