package com.himanshoe.charty.bar.common.calculations

import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.himanshoe.charty.bar.model.BarData

internal fun getTopLeft(
    index: Int,
    barWidth: MutableState<Float>,
    size: Size,
    barData: BarData,
    yScalableFactor: Float
) = Offset(
    x = index.times(barWidth.value.times(1.2F)),
    y = size.height.minus(barData.yValue.times(yScalableFactor))
)

internal fun getTopRight(
    index: Int,
    barWidth: MutableState<Float>,
    size: Size,
    barData: BarData,
    yChunck: Float
) = Offset(
    x = index.plus(1).times(barWidth.value.times(1.2F)),
    y = size.height.minus(barData.yValue.times(yChunck))
)
