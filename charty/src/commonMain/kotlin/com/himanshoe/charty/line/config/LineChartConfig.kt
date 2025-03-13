package com.himanshoe.charty.line.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import com.himanshoe.charty.common.ChartColor

data class LineChartConfig(
    val axisConfig: LineChartAxisConfig = LineChartAxisConfig(),
    val gridConfig: LineChartGridConfig = LineChartGridConfig(),
    val lineConfig: LineConfig = LineConfig(),
    val interactionTooltipConfig: InteractionTooltipConfig = InteractionTooltipConfig()
)

data class LineChartAxisConfig(
    val axisLineWidth: Float = 2f
)

data class LineChartGridConfig(
    val gridLineWidth: Float = 1f,
    val gridLinePathEffect: PathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
)

data class LineConfig(
    val lineChartStrokeWidth: Float = 5f,
    val lineCap: StrokeCap = StrokeCap.Round,
    val drawPointerCircle: Boolean = false,
    val showValueOnLine: Boolean = false,
    val valueTextStyle: TextStyle? = null,
    val valueTextColor: ChartColor = ChartColor.Solid(Color.Black),
)

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
