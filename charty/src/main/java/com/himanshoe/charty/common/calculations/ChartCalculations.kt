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

internal fun unboundDataToOffset(
    size: Size,
    xData: Float,
    xMax: Float,
    xRange: Float,
    yData: Float,
    yScaleFactor: Float
): Offset {
    val currentXDiff = xMax.minus(xData)
    val rangeDiff = xRange.minus(currentXDiff)
    val x = rangeDiff.div(xRange).times(size.width)
    val y = size.height.minus(yData.times(yScaleFactor))
    return Offset(x, y)
}
