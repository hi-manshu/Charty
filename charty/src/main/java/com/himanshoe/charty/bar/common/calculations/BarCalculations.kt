package com.himanshoe.charty.bar.common.calculations

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

internal fun getTopLeft(
    index: Int,
    barWidth: Float,
    size: Size,
    yValue: Float,
    yScalableFactor: Float
) = Offset(
    x = index.times(barWidth.times(1.2F)),
    y = size.height.minus(yValue.times(yScalableFactor))
)

internal fun getStackedTopLeft(
    index: Int,
    barWidth: Float,
    barHeight: Float,
) = Offset(
    x = index.times(barWidth.times(1.2F)),
    y = (barHeight)
)

internal fun getTopRight(
    index: Int,
    barWidth: Float,
    size: Size,
    yValue: Float,
    yScaleFactor: Float
) = Offset(
    x = index.plus(1).times(barWidth.times(1.2F)),
    y = size.height.minus(yValue.times(yScaleFactor))
)
