package com.himanshoe.charty.combined.common.calculations

import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.himanshoe.charty.combined.model.CombinedBarData

internal fun getTopLeft(
    index: Int,
    barWidth: MutableState<Float>,
    size: Size,
    barData: CombinedBarData,
    yScalableFactor: Float
) = Offset(
    x = index.times(barWidth.value.times(1.2F)),
    y = size.height.minus(barData.yBarValue.times(yScalableFactor))
)

internal fun getTopRight(
    index: Int,
    barWidth: MutableState<Float>,
    size: Size,
    barData: CombinedBarData,
    yScaleFactor: Float
) = Offset(
    x = index.plus(1).times(barWidth.value.times(1.2F)),
    y = size.height.minus(barData.yBarValue.times(yScaleFactor))
)
