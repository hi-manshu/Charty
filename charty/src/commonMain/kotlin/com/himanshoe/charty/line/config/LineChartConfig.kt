package com.himanshoe.charty.line.config

import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap

/**
 * Data class representing the configuration for a line chart.
 *
 * @property axisLineWidth The width of the axis lines.
 * @property gridLineWidth The width of the grid lines.
 * @property lineChartStrokeWidth The width of the line chart stroke.
 * @property gridLinePathEffect The path effect for the grid lines.
 * @property lineCap The stroke cap style for the line chart.
 * @property drawPointerCircle A boolean indicating whether to draw a pointer circle on the line.
 */
data class LineChartConfig(
    val axisLineWidth: Float = 2f,
    val gridLineWidth: Float = 1f,
    val lineChartStrokeWidth: Float = 5f,
    val gridLinePathEffect: PathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
    val lineCap: StrokeCap = StrokeCap.Round,
    val drawPointerCircle: Boolean = false,
)
