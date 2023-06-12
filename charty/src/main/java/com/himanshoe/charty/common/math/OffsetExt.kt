package com.himanshoe.charty.common.math

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

internal fun chartDataToOffset(
    index: Int,
    bound: Float,
    size: Size,
    data: Float,
    scaleFactor: Float
): Offset {
    val startX = index * bound * 1.2F
    val endX = (index + 1) * bound * 1.2F
    val y = size.height - data * scaleFactor
    return Offset((startX + endX) / 2F, y)
}
