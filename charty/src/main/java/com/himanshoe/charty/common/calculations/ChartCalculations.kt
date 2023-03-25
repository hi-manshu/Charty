package com.himanshoe.charty.common.calculations

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

private const val BoundFactor = 1.2F

internal fun dataToOffSet(
    index: Int,
    bound: Float,
    size: Size,
    data: Float,
    yScaleFactor: Float
): Offset {
    val startX = index.times(bound.times(BoundFactor))
    val endX = index.plus(1).times(bound.times(BoundFactor))
    val y = size.height.minus(data.times(yScaleFactor))
    return Offset(((startX.plus(endX)).div(2F)), y)
}

/**
 * Provides point offsets calculated by their position relative to the screen's width and height.
 */
internal fun unboundDataToOffset(
    size: Size,
    xData: Float,
    xMax: Float,
    xRange: Float,
    yData: Float,
    yMax: Float,
    yRange: Float
): Offset {
    val currentXDiff = xMax.minus(xData)
    val xRangeDiff = xRange.minus(currentXDiff)
    val x = xRangeDiff.div(xRange).times(size.width)

    val currentYDiff = yMax.minus(yData)
    val yRangeDiff = yRange.minus(currentYDiff)
    val y = size.height.minus(yRangeDiff.div(yRange).times(size.height))

    return Offset(x, y)
}
