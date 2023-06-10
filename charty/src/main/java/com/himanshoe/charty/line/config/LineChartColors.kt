package com.himanshoe.charty.line.config

import androidx.compose.ui.graphics.Color

data class LineChartColors(
     val lineColor: List<Color> = emptyList(),
     val dotColor: List<Color> = emptyList(),
     val backgroundColors: List<Color> = emptyList(),
)

data class CurvedLineChartColors(
     val dotColor: List<Color> = emptyList(),
     val backgroundColors: List<Color> = emptyList(),
     val contentColor: List<Color> = emptyList(),
)