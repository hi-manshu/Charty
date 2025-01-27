package com.himanshoe.charty.bar.config

import androidx.compose.ui.graphics.Color

data class BarChartColorConfig(
    val defaultGradientBarColors: List<Color>,
    val negativeGradientBarColors: List<Color>,
    val barBackgroundColor: Color = Color(0xD3D3D350),
    val gridLineColor: Color = Color(0xFFD3D3DE),
    val axisLineColor: Color = Color(0xFF444444),
) {
    companion object {
        fun default() =
            BarChartColorConfig(
                defaultGradientBarColors =
                listOf(
                    Color.Blue,
                    Color.Green,
                ),
                negativeGradientBarColors =
                listOf(
                    Color.Red,
                    Color.Black,
                ),
            )
    }
}
