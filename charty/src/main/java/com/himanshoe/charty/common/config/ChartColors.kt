package com.himanshoe.charty.common.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ChartColors(
    val contentColor: List<Color> = emptyList(),
    val backgroundColors: List<Color> = emptyList(),
)
