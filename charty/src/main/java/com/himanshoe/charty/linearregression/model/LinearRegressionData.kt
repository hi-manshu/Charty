package com.himanshoe.charty.linearregression.model

data class LinearRegressionData(val xValue: Float, val yValue: Float)

internal fun List<LinearRegressionData>.maxYValue() = maxOf { it.yValue }
internal fun List<LinearRegressionData>.minYValue() = minOf { it.yValue }
internal fun List<LinearRegressionData>.maxXValue() = maxOf { it.xValue }
internal fun List<LinearRegressionData>.minXValue() = minOf { it.xValue }
