/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.stacked

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.common.ChartSurface
import com.himanshoe.charty.common.ComposeList
import com.himanshoe.charty.common.config.AxisConfig
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.common.config.ChartyLabelTextConfig
import com.himanshoe.charty.common.toComposeList
import com.himanshoe.charty.common.ui.drawGridLines
import com.himanshoe.charty.common.ui.drawXAxis
import com.himanshoe.charty.common.ui.drawYAxis
import com.himanshoe.charty.stacked.config.StackBarData

/**
 * Composable function that renders a stacked bar chart.
 *
 * @param stackBarData The collection of stack bar data.
 * @param modifier The modifier for the chart.
 * @param axisConfig The configuration for the chart axes.
 * @param padding The padding around the chart.
 * @param spacing The spacing between stack bars.
 * @param textLabelTextConfig The configuration for the text labels in the chart.
 */
@Composable
fun StackedBarChart(
    stackBarData: ComposeList<StackBarData>,
    modifier: Modifier = Modifier,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    padding: Dp = 16.dp,
    spacing: Dp = 4.dp,
    textLabelTextConfig: ChartyLabelTextConfig = ChartDefaults.defaultTextLabelConfig(),
) {
    val allDataPoints = stackBarData.data.map {
        it.dataPoints.sum()
    }.toMutableList().apply {
        add(0F)
    }

    val maxSum = stackBarData.data.maxOfOrNull {
        it.dataPoints.sum()
    } ?: 0f

    val textMeasurer = rememberTextMeasurer()

    var chartWidth by remember { mutableStateOf(0f) }
    var chartHeight by remember { mutableStateOf(0f) }

    ChartSurface(
        modifier = modifier,
        padding = padding,
        axisConfig = axisConfig,
        chartData = allDataPoints.toComposeList()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { size ->
                    chartWidth = size.width.toFloat()
                    chartHeight = size.height.toFloat()
                }
                .drawBehind {
                    if (axisConfig.showAxes) {
                        drawYAxis(axisConfig.axisColor, axisConfig.axisStroke)
                        drawXAxis(axisConfig.axisColor, axisConfig.axisStroke)
                    }
                    if (axisConfig.showGridLines) {
                        drawGridLines(chartWidth, chartHeight, padding.toPx())
                    }
                }
        ) {
            val width = chartWidth - (padding).toPx()
            val height = chartHeight - (padding).toPx()
            val scaleFactor = chartHeight / maxSum

            val barCount = stackBarData.data.size
            val totalSpacing = (barCount - 1) * spacing.toPx()
            val barWidth = (width - totalSpacing) / barCount.toFloat()

            stackBarData.data.fastForEachIndexed { index, stackValues ->
                val x = (index * (barWidth + spacing.toPx()))
                var startY = height + padding.toPx()

                if (axisConfig.showGridLabel) {
                    when {
                        barCount < 14 -> {
                            drawLabel(
                                stackValues = stackValues,
                                index = index,
                                barWidth = barWidth + spacing.toPx(),
                                chartHeight = chartHeight,
                                textLabelTextConfig = textLabelTextConfig,
                                textMeasurer = textMeasurer
                            )
                        }

                        index == 0 || index == barCount / 2 || index == barCount - 1 -> {
                            drawLabel(
                                stackValues = stackValues,
                                index = index,
                                barWidth = barWidth + spacing.toPx(),
                                chartHeight = chartHeight,
                                textLabelTextConfig = textLabelTextConfig,
                                textMeasurer = textMeasurer
                            )
                        }
                    }
                }

                stackValues.dataPoints.fastForEachIndexed { stackIndex, value ->
                    val stackHeight = value * scaleFactor
                    val endY = startY - stackHeight

                    val clampedEndY = startY.coerceAtMost(endY)
                    val clampedStackHeight = startY - clampedEndY

                    drawRect(
                        color = stackValues.colors.getOrElse(stackIndex) { Color.Gray },
                        topLeft = Offset(x, clampedEndY),
                        size = Size(barWidth, clampedStackHeight)
                    )

                    startY = endY
                }
            }
        }
    }
}

private fun DrawScope.drawLabel(
    stackValues: StackBarData,
    index: Int,
    barWidth: Float,
    chartHeight: Float,
    textLabelTextConfig: ChartyLabelTextConfig,
    textMeasurer: TextMeasurer
) {
    val label = stackValues.label
    val labelY = chartHeight + size.width / 20
    val labelX = when (index) {
        0 -> 0.5f * barWidth
        else -> (index * barWidth) + (barWidth / 2f)
    }

    val textStyle = TextStyle(
        color = textLabelTextConfig.textColor,
        textAlign = TextAlign.Center,
        fontSize = (size.width / 90).sp
    )

    val textMeasurement = textMeasurer.measure(
        label,
        textStyle
    )

    drawText(
        text = label,
        textMeasurer = textMeasurer,
        style = textStyle,
        topLeft = Offset(
            labelX - (textMeasurement.size.width / 2),
            labelY - (textMeasurement.size.height / 2)
        ),
        size = Size(
            width = textMeasurement.size.width.toFloat(),
            height = textMeasurement.size.height.toFloat()
        )
    )
}
