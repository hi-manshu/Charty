package com.himanshoe.charty.bar.config

import androidx.compose.ui.graphics.Color
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.common.asGradientChartColor

/**
 * Configuration class for bar chart colors.
 *
 * @property fillGradientColors The gradient colors for the bars.
 * @property negativeGradientBarColors The gradient colors for negative bars.
 * @property barBackgroundColor The background color of the bars, defaults to a light gray color.
 * @property gridLineColor The color of the grid lines, defaults to a light gray color.
 * @property axisLineColor The color of the axis lines, defaults to a dark gray color.
 */
data class BarChartColorConfig(
    val fillGradientColors: ChartColor,
    val negativeGradientBarColors: ChartColor,
    val barBackgroundColor: ChartColor = ChartColor.Solid(Color(0xD3D3D350)),
    val gridLineColor: ChartColor = ChartColor.Solid(Color(0xFFD3D3DE)),
    val axisLineColor: ChartColor = ChartColor.Solid(Color(0xFF444444)),
) {
    companion object {
        /**
         * Provides a default configuration for bar chart colors.
         *
         * @return A `BarChartColorConfig` object with default color settings.
         */
        fun default() =
            BarChartColorConfig(
                fillGradientColors = listOf(
                    Color(0xFFD9A7C7),
                    Color(0xFFFFFCDC),
                ).asGradientChartColor(),
                negativeGradientBarColors = listOf(
                    Color(0xFFCB356B),
                    Color(0xFFBD3F32),
                ).asGradientChartColor()

            )
    }
}