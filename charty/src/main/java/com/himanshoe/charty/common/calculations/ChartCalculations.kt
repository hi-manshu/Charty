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
