package com.himanshoe.charty.line.config

import androidx.compose.ui.graphics.Color
import com.himanshoe.charty.common.ChartColor

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