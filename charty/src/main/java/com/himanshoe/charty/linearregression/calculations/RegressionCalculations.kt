package com.himanshoe.charty.linearregression.calculations

import com.himanshoe.charty.linearregression.model.LinearRegressionData
import kotlin.math.pow

/**
 * Calculates the regression slope value.
 */
internal fun betaSlopeCalculation(data: List<LinearRegressionData>): Double {
    val independentMean = data.map { it.xValue }.average()
    val dependentMean = data.map { it.yValue }.average()

    val numerator = data.sumOf { point -> (point.xValue - independentMean) * (point.yValue - dependentMean) }
    val denominator = data.sumOf { point -> (point.xValue - independentMean).pow(2) }

    return numerator/denominator
}

/**
 * Calculates the estimated y-intercept value of the regression line.
 */
internal fun yInterceptCalculation(data: List<LinearRegressionData>, betaSlope: Double) =
    data.sumOf { point -> point.yValue - (point.xValue * betaSlope) } / data.size

/**
 * Calculates the regression end points for plotting.
 */
internal fun calculateRegressionEndPoints(
    data: Map<Float, List<Float>>,
    betaSlope: Double,
    yIntercept: Double
): List<LinearRegressionData> {
    val first = LinearRegressionData(
        xValue = data.entries.first().key,
        yValue = ((betaSlope * data.entries.first().key) + yIntercept).toFloat()
    )

    val last = LinearRegressionData(
        xValue = data.entries.last().key,
        yValue = ((betaSlope * data.entries.last().key) + yIntercept).toFloat()
    )

    return listOf(first, last)
}
