package com.himanshoe.charty.point.model

import androidx.compose.ui.graphics.Color
import com.himanshoe.charty.common.ChartColor

/**
 * Data class representing the color configuration for a point chart.
 *
 * @property axisColor The color of the axis lines.
 * @property gridLineColor The color of the grid lines.
 * @property circleColor The color of the circles in the chart.
 * @property strokeColor The stroke color of the circles, with a default gradient based on the circle color.
 * @property selectionBarColor The color of the selection bar.
 */
data class PointChartColorConfig(
    val axisColor: ChartColor,
    val gridLineColor: ChartColor,
    val circleColor: ChartColor,
    val strokeColor: ChartColor = ChartColor.Gradient(circleColor.value.map {
        it.copy(alpha = 0.5f)
    }),
    val selectionBarColor: ChartColor
) {
    companion object {
        /**
         * Returns the default configuration for the point chart colors.
         *
         * @return A PointChartColorConfig object with default color settings.
         */
        fun default() = PointChartColorConfig(
            axisColor = ChartColor.Gradient(listOf(Color.Gray, Color.Gray)),
            gridLineColor = ChartColor.Gradient(listOf(Color.Gray, Color.Gray)),
            circleColor = ChartColor.Gradient(
                listOf(
                    Color(0xFFCB356B),
                    Color(0xFFBD3F32),
                )
            ),
            selectionBarColor = ChartColor.Gradient(
                listOf(
                    Color.White.copy(alpha = 0.3f),
                    Color.Gray.copy(alpha = 0.2f),
                )
            ),
            strokeColor = ChartColor.Gradient(
                listOf(
                    Color(0xFFFFFFFF).copy(alpha = 0.3f),
                    Color(0xFFff6a00).copy(alpha = 0.3f)
                )
            )
        )
    }
}