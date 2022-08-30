package com.himanshoe.charty.combined.common.calculations

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.himanshoe.charty.combined.model.CombinedBarData

internal fun getTopLeft(
    index: Int,
    barWidth: Float,
    size: Size,
    barData: CombinedBarData,
    yScalableFactor: Float
) = Offset(
    x = index.times(barWidth.times(1.2F)),
    y = size.height.minus(barData.yBarValue.times(yScalableFactor))
)

internal fun getTopRight(
    index: Int,
    barWidth: Float,
    size: Size,
    barData: CombinedBarData,
    yScaleFactor: Float
) = Offset(
    x = index.plus(1).times(barWidth.times(1.2F)),
    y = size.height.minus(barData.yBarValue.times(yScaleFactor))
)
