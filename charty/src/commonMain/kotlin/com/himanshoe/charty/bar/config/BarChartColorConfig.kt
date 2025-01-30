package com.himanshoe.charty.bar.config

import androidx.compose.ui.graphics.Color

data class BarChartColorConfig(
    val fillGradientColors: List<Color>,
    val negativeGradientBarColors: List<Color>,
    val barBackgroundColor: Color = Color(0xD3D3D350),
    val gridLineColor: Color = Color(0xFFD3D3DE),
    val axisLineColor: Color = Color(0xFF444444),
) {
    companion object {
        fun default() =
            BarChartColorConfig(
                fillGradientColors = listOf(
                    Color(0xFFD9A7C7),
                    Color(0xFFFFFCDC),
                ),
                negativeGradientBarColors = listOf(
                    Color(0xFFCB356B),
                    Color(0xFFBD3F32),
                ),
            )
    }
}
