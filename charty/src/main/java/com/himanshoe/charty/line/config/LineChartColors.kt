/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.line.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class LineChartColors(
    val lineColor: List<Color> = emptyList(),
    val dotColor: List<Color> = emptyList(),
    val backgroundColors: List<Color> = emptyList(),
)

@Immutable
data class CurvedLineChartColors(
    val dotColor: List<Color> = emptyList(),
    val backgroundColors: List<Color> = emptyList(),
    val contentColor: List<Color> = emptyList(),
)
