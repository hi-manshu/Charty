package com.himanshoe.charty.line.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import com.himanshoe.charty.common.ChartColor

/**
 * Configuration for the line chart.
 *
 * @property axisConfig Configuration for the chart axis.
 * @property gridConfig Configuration for the chart grid.
 * @property lineConfig Configuration for the line in the chart.
 * @property interactionTooltipConfig Configuration for the interaction tooltip.
 */
data class LineChartConfig(
    val axisConfig: LineChartAxisConfig = LineChartAxisConfig(),
    val gridConfig: LineChartGridConfig = LineChartGridConfig(),
    val lineConfig: LineConfig = LineConfig(),
    val interactionTooltipConfig: InteractionTooltipConfig = InteractionTooltipConfig()
)

/**
 * Configuration for the chart axis.
 *
 * @property axisLineWidth Width of the axis lines.
 */
data class LineChartAxisConfig(
    val axisLineWidth: Float = 2f
)

/**
 * Configuration for the chart grid.
 *
 * @property gridLineWidth Width of the grid lines.
 * @property gridLinePathEffect Path effect for the grid lines.
 */
data class LineChartGridConfig(
    val gridLineWidth: Float = 1f,
    val gridLinePathEffect: PathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
)

/**
 * Configuration for the line in the chart.
 *
 * @property lineChartStrokeWidth Stroke width of the line.
 * @property lineCap Stroke cap style of the line.
 * @property drawPointerCircle Whether to draw a pointer circle on the line.
 * @property showValueOnLine Whether to show values on the line.
 * @property valueTextStyle Text style for the values on the line.
 * @property valueTextColor Text color for the values on the line.
 */
data class LineConfig(
    val lineChartStrokeWidth: Float = 5f,
    val lineCap: StrokeCap = StrokeCap.Round,
    val drawPointerCircle: Boolean = false,
    val showValueOnLine: Boolean = false,
    val valueTextStyle: TextStyle? = null,
    val valueTextColor: ChartColor = ChartColor.Solid(Color.Black),
)

/**
 * Configuration for the interaction tooltip.
 *
 * @property isLongPressDragEnabled Whether long press drag is enabled.
 * @property textStyle Text style for the tooltip text.
 * @property textColor Text color for the tooltip text.
 * @property containerColor Background color for the tooltip container.
 * @property indicatorLineWidth Width of the indicator line.
 * @property indicatorLineColor Color of the indicator line.
 * @property indicatorLinePathEffect Path effect for the indicator line.
 */
data class InteractionTooltipConfig(
    val isLongPressDragEnabled: Boolean = false,
    val textStyle: TextStyle? = null,
    val textColor: ChartColor = ChartColor.Solid(Color.White),
    val containerColor: ChartColor = ChartColor.Gradient(
        listOf(
            Color(0xFFCB356B),
            Color(0xFFBD3F32)
        )
    ),
    val indicatorLineWidth: Float = 5f,
    val indicatorLineColor: ChartColor = ChartColor.Gradient(
        listOf(
            Color(0xFFFFFFFF),
            Color(0xFFff6a00)
        )
    ),
    val indicatorLinePathEffect: PathEffect? = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
)
