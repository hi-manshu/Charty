package com.himanshoe.charty.line.common

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.line.model.LineData

internal fun getLineTopLeft(
    index: Int,
    barWidth: Float,
    size: Size,
    lineData: LineData,
    yScalableFactor: Float
) = Offset(
    x = index.times(barWidth.times(1.2F)),
    y = size.height.minus(lineData.yValue.times(yScalableFactor))
)

internal fun getLineTopRight(
    index: Int,
    barWidth: Float,
    size: Size,
    barData: BarData,
    yScaleFactor: Float
) = Offset(
    x = index.plus(1).times(barWidth.times(1.2F)),
    y = size.height.minus(barData.yValue.times(yScaleFactor))
)
