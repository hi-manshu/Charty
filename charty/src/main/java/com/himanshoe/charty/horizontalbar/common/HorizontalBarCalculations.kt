package com.himanshoe.charty.horizontalbar.common

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.himanshoe.charty.horizontalbar.model.HorizontalBarData

internal fun getTopLeft(
    index: Int,
    barHeight: Float,
    size: Size,
    horizontalBarData: HorizontalBarData,
    scalableFactor: Float
) = Offset(
    y = index.times(barHeight.times(1.2F)),
    x = size.width.minus(horizontalBarData.xValue.times(scalableFactor))
)

internal fun getBottomLeft(
    index: Int,
    barHeight: Float,
    size: Size,
    horizontalBarData: HorizontalBarData,
    scalableFactor: Float
) = Offset(
    y = index.plus(1).times(barHeight.times(1.2F)),
    x = size.width.minus(horizontalBarData.xValue.times(scalableFactor))

)
