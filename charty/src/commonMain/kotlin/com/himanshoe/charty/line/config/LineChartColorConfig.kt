package com.himanshoe.charty.line.config

import androidx.compose.ui.graphics.Color
import com.himanshoe.charty.common.ChartColor

/**
 * Data class representing the color configuration for a line chart.
 *
 * @property axisColor The color of the axis lines.
 * @property gridLineColor The color of the grid lines.
 * @property lineColor The color of the line in the chart.
 * @property lineFillColor The fill color under the line in the chart, with a default gradient based on the line color.
 * @property selectionBarColor The color of the selection bar.
 */
data class LineChartColorConfig(
    val axisColor: ChartColor,
    val gridLineColor: ChartColor,
    val lineColor: ChartColor,
    val lineFillColor: ChartColor = ChartColor.Gradient(lineColor.value.map {
        it.copy(alpha = 0.2f)
    }),
    val selectionBarColor: ChartColor
) {
    companion object {
        /**
         * Returns the default configuration for the line chart colors.
         *
         * @return A LineChartColorConfig object with default color settings.
         */
        fun default() = LineChartColorConfig(
            axisColor = ChartColor.Gradient(listOf(Color.Gray, Color.Gray)),
            gridLineColor = ChartColor.Gradient(listOf(Color.Gray, Color.Gray)),
            lineColor = ChartColor.Gradient(
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
            lineFillColor = ChartColor.Gradient(
                listOf(
                    Color(0xFFFFFFFF).copy(alpha = 0.3f),
                    Color(0xFFff6a00).copy(alpha = 0.3f)
                )
            )
        )
    }
}