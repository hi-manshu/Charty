/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.candle

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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.candle.config.CandleStickConfig
import com.himanshoe.charty.candle.config.CandleStickDefaults
import com.himanshoe.charty.candle.model.CandleData
import com.himanshoe.charty.common.ChartSurface
import com.himanshoe.charty.common.ComposeList
import com.himanshoe.charty.common.config.AxisConfig
import com.himanshoe.charty.common.config.ChartDefaults
import com.himanshoe.charty.common.toComposeList
import com.himanshoe.charty.common.ui.drawGridLines
import com.himanshoe.charty.common.ui.drawXAxis
import com.himanshoe.charty.common.ui.drawYAxis

/**
 * Renders a candlestick chart based on the provided candle data.
 *
 * @param candleData The list of candlestick data.
 * @param modifier The modifier for the chart.
 * @param axisConfig The configuration for the chart axes.
 * @param padding The padding around the chart.
 * @param candleConfig The configuration for the candlesticks.
 */
@Composable
fun CandleStickChart(
    candleData: ComposeList<CandleData>,
    modifier: Modifier = Modifier,
    axisConfig: AxisConfig = ChartDefaults.axisConfigDefaults(),
    padding: Dp = 16.dp,
    candleConfig: CandleStickConfig = CandleStickDefaults.defaultCandleStickConfig(),
) {
    val listOfAxisValues = remember(candleData.data) {
        candleData.data.flatMap { listOf(it.high, it.low, it.open, it.close) }
            .distinct()
            .sorted()
    }
    val maxValue = candleData.data.maxOf { maxOf(it.high, it.open, it.close) }
    val minValue = candleData.data.minOf { minOf(it.low, it.open, it.close) }

    var chartWidth by remember { mutableStateOf(0f) }
    var chartHeight by remember { mutableStateOf(0f) }

    ChartSurface(
        modifier = modifier,
        padding = padding,
        axisConfig = axisConfig,
        chartData = listOfAxisValues.toComposeList(),
        content = {
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
                val innerWidth = chartWidth - padding.toPx()
                val innerHeight = chartHeight - padding.toPx() * 1.1F

                val candleCount = candleData.data.size
                val candleWidth = if (candleCount < 15) {
                    innerWidth / 15
                } else {
                    innerWidth / candleCount
                }

                candleData.data.fastForEachIndexed { index, data ->
                    val x = padding.toPx() + index * candleWidth
                    val yHigh = calculateYPosition(
                        data.high,
                        maxValue,
                        minValue,
                        innerHeight
                    ) + padding.toPx()

                    val yLow = calculateYPosition(
                        data.low,
                        maxValue,
                        minValue,
                        innerHeight
                    ) + padding.toPx()

                    val yOpen = calculateYPosition(
                        data.open,
                        maxValue,
                        minValue,
                        innerHeight
                    ) + padding.toPx()

                    val yClose = calculateYPosition(
                        data.close,
                        maxValue,
                        minValue,
                        innerHeight
                    ) + padding.toPx()

                    val wickStartX = x + candleWidth.div(2)
                    val wickEndX = x + candleWidth.div(2)

                    // Draw wick
                    drawLine(
                        color = candleConfig.wickColor,
                        start = Offset(wickStartX, yHigh),
                        end = Offset(wickEndX, yLow),
                        strokeWidth = calculateWickStrokeWidth(
                            candleWidth,
                            candleConfig.wickWidthScale
                        )
                    )

                    // Draw body
                    val bodyStartY = minOf(yOpen, yClose)
                    val bodyEndY = maxOf(yOpen, yClose)
                    val bodyWidth = calculateBodyWidth(candleWidth)

                    drawRect(
                        color = if (data.close >= data.open) candleConfig.positiveColor else candleConfig.negativeColor,
                        topLeft = Offset(x, bodyStartY),
                        size = Size(bodyWidth, bodyEndY - bodyStartY)
                    )
                }
            }
        }
    )
}

private fun calculateWickStrokeWidth(candleWidth: Float, wickWidthScale: Float) =
    candleWidth * wickWidthScale

private fun calculateBodyWidth(candleWidth: Float) = candleWidth * 0.8f

private fun calculateYPosition(
    value: Float,
    maxValue: Float,
    minValue: Float,
    chartHeight: Float
): Float {
    return chartHeight * (1 - (value - minValue) / (maxValue - minValue))
}
