/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.common.math

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

/**
 * Calculates the offset for a data point on a chart.
 *
 * @param index The index of the data point.
 * @param bound The width of each data point.
 * @param size The size of the chart.
 * @param data The value of the data point.
 * @param scaleFactor The scale factor for the data points.
 * @return The offset of the data point.
 */
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
