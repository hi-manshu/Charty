package com.himanshoe.charty.bar.common.calculations

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.himanshoe.charty.bar.model.BarData

internal fun getBarTopLeft(
    index: Int,
    barWidth: Float,
    size: Size,
    barData: BarData,
    yScalableFactor: Float
) = Offset(
    x = index.times(barWidth.times(1.2F)),
    y = size.height.minus(barData.yValue.times(yScalableFactor))
)

internal fun getBarTopRight(
    index: Int,
    barWidth: Float,
    size: Size,
    barData: BarData,
    yScaleFactor: Float
) = Offset(
    x = index.plus(1).times(barWidth.times(1.2F)),
    y = size.height.minus(barData.yValue.times(yScaleFactor))
)
