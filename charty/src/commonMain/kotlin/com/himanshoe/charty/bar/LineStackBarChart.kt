package com.himanshoe.charty.bar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastAny
import androidx.compose.ui.util.fastFlatMap
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.bar.config.BarChartColorConfig
import com.himanshoe.charty.bar.config.StackBarConfig
import com.himanshoe.charty.bar.model.StackBarData
import com.himanshoe.charty.bar.modifier.drawAxisLineForVerticalChart
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.common.TargetConfig
import com.himanshoe.charty.common.drawTargetLineIfNeeded
import com.himanshoe.charty.common.getDrawingPath
import kotlin.math.absoluteValue

/**
 * A composable function that displays a line stacked bar chart.
 *
 * @param data A lambda function that returns a list of `StackBarData` representing the data to be displayed.
 * @param modifier A `Modifier` for this composable.
 * @param target An optional target value to be displayed as a line on the chart.
 * @param targetConfig Configuration for the target line.
 * @param stackBarConfig Configuration for the stacked bar chart.
 * @param barChartColorConfig Configuration for the colors used in the bar chart.
 * @param labelConfig Configuration for the labels displayed on the chart.
 * @param onBarClick A lambda function to be invoked when a bar is clicked, with the index and data of the clicked bar.
 */
@Composable
fun LineStackedBarChart(
    data: () -> List<StackBarData>,
    modifier: Modifier = Modifier,
    target: Float? = null,
    targetConfig: TargetConfig = TargetConfig.default(),
    stackBarConfig: StackBarConfig = StackBarConfig.default(),
    barChartColorConfig: BarChartColorConfig = BarChartColorConfig.default(),
    labelConfig: LabelConfig = LabelConfig.default(),
    onBarClick: (Int, StackBarData) -> Unit = { _, _ -> },
) {
    LineStackBarChartContent(
        data = data,
        stackBarConfig = stackBarConfig,
        labelConfig = labelConfig,
        modifier = modifier,
        barChartColorConfig = barChartColorConfig,
        target = target,
        targetConfig = targetConfig,
        onBarClick = onBarClick
    )
}

@Composable
private fun LineStackBarChartContent(
    data: () -> List<StackBarData>,
    stackBarConfig: StackBarConfig,
    labelConfig: LabelConfig,
    modifier: Modifier,
    barChartColorConfig: BarChartColorConfig,
    target: Float?,
    targetConfig: TargetConfig,
    onBarClick: (Int, StackBarData) -> Unit
) {
    val lineData = data()
    val displayData = remember(lineData) { getDisplayData(lineData, stackBarConfig.minimumBarCount) }
    val maxValue =
        remember(displayData) { displayData.maxOfOrNull { it.values.sum().absoluteValue } ?: 0f }
    val hasNegativeValues = remember(lineData) { displayData.fastFlatMap { it.values }.fastAny { it < 0 } }
    val textMeasurer = rememberTextMeasurer()
    val bottomPadding = if (labelConfig.showXLabel && !hasNegativeValues) 8.dp else 0.dp
    val leftPadding = if (labelConfig.showYLabel) 24.dp else 0.dp

    var clickedOffset by mutableStateOf(Offset.Zero)
    var clickedBarIndex by mutableIntStateOf(-1)

    StackBarChartScaffold(
        maxValue = maxValue,
        bottomPadding = bottomPadding,
        leftPadding = leftPadding,
        hasNegativeValues = hasNegativeValues,
        modifier = modifier,
        displayData = displayData,
        barChartColorConfig = barChartColorConfig,
        labelConfig = labelConfig,
        textMeasurer = textMeasurer,
        showGridLines = stackBarConfig.showGridLines,
        showAxisLines = stackBarConfig.showAxisLines,
        onBarClick = { clickedOffset = it }
    ) { canvasWidth, canvasHeight ->
        val gap = canvasWidth / (displayData.size * 10)
        val barWidth = (canvasWidth - gap * (displayData.size - 1)) / displayData.size / 3

        target?.let {
            drawTargetLineIfNeeded(
                canvasWidth = canvasWidth,
                targetConfig = targetConfig,
                yPoint = (canvasHeight - (it / maxValue) * canvasHeight)
            )
        }

        displayData.fastForEachIndexed { index, stackBarData ->
            var accumulatedHeight = 0f
            stackBarData.values.fastForEachIndexed { valueIndex, value ->
                val height = value.absoluteValue / maxValue * canvasHeight
                val expandedHeight = if (clickedBarIndex == index) (height * 1.05f) else height
                val topLeftY = canvasHeight - accumulatedHeight - expandedHeight
                val color = stackBarData.colors[valueIndex].value

                val (individualBarTopLeft, individualBarRectSize, cornerRadius) = getBarTopLeftSizeAndRadius(
                    index = index,
                    barWidth = barWidth,
                    gap = gap,
                    topLeftY = topLeftY,
                    height = height,
                    stackBarConfig = stackBarConfig,
                    valueIndex = valueIndex,
                    stackBarData = stackBarData
                )

                if (isClickInsideBar(clickedOffset, individualBarTopLeft, individualBarRectSize)) {
                    clickedBarIndex = index
                    onBarClick(index, stackBarData)
                }

                getDrawingPath(
                    individualBarTopLeft = individualBarTopLeft,
                    individualBarRectSize = individualBarRectSize.copy(height = expandedHeight),
                    cornerRadius = cornerRadius
                ).let { path ->
                    val brush = Brush.linearGradient(
                        colors = color,
                        start = Offset(individualBarTopLeft.x, individualBarTopLeft.y),
                        end = Offset(
                            x = individualBarTopLeft.x + individualBarRectSize.width,
                            y = individualBarTopLeft.y + expandedHeight
                        )
                    )
                    drawPath(path = path, brush = brush)
                }
                accumulatedHeight += height
            }
        }
    }
}

private fun getBarTopLeftSizeAndRadius(
    index: Int,
    barWidth: Float,
    gap: Float,
    topLeftY: Float,
    height: Float,
    stackBarConfig: StackBarConfig,
    valueIndex: Int,
    stackBarData: StackBarData
): Triple<Offset, Size, CornerRadius> {
    val individualBarTopLeft = Offset(
        x = if (index == 0) barWidth + gap else index * (barWidth * 3 + gap) + barWidth,
        y = topLeftY
    )

    val individualBarRectSize = Size(
        width = barWidth,
        height = height
    )
    val cornerRadius =
        if (stackBarConfig.showCurvedBar && valueIndex == stackBarData.values.lastIndex) {
            CornerRadius(barWidth / 2, barWidth / 2)
        } else {
            CornerRadius.Zero
        }
    return Triple(individualBarTopLeft, individualBarRectSize, cornerRadius)
}

@Composable
internal fun StackBarChartScaffold(
    maxValue: Float,
    bottomPadding: Dp,
    leftPadding: Dp,
    hasNegativeValues: Boolean,
    showAxisLines: Boolean,
    showGridLines: Boolean,
    textMeasurer: TextMeasurer,
    modifier: Modifier = Modifier,
    displayData: List<StackBarData> = emptyList(),
    barChartColorConfig: BarChartColorConfig = BarChartColorConfig.default(),
    labelConfig: LabelConfig = LabelConfig.default(),
    onBarClick: (Offset) -> Unit = {},
    content: DrawScope.(Float, Float) -> Unit,
) {
    val dataCount = displayData.count()
    val step = maxValue / 4
    Canvas(
        modifier = modifier
            .padding(bottom = bottomPadding, start = leftPadding)
            .fillMaxSize()
            .drawWithCache {
                val barWidth = size.width * 9 / (dataCount * 10)
                onDrawBehind {
                    for (i in 0..4) {
                        val yValue = i * step
                        val displayValue = yValue.toString().take(4)
                        val yOffset = size.height - (yValue / maxValue) * size.height
                        if (showGridLines) {
                            // Draw horizontal grid line
                            drawLine(
                                brush = Brush.linearGradient(barChartColorConfig.gridLineColor.value),
                                start = Offset(0f, yOffset),
                                end = Offset(size.width, yOffset),
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                        if (labelConfig.showYLabel && !hasNegativeValues) {
                            val textLayoutResult = textMeasurer.measure(
                                text = displayValue,
                                style = TextStyle(fontSize = (size.width / displayData.count() / 10).sp),
                                overflow = TextOverflow.Clip,
                                maxLines = 1,
                            )
                            drawText(
                                textLayoutResult = textLayoutResult,
                                brush = Brush.linearGradient(labelConfig.textColor.value),
                                topLeft = Offset(
                                    -textLayoutResult.size.width - 8f,
                                    y = yOffset - textLayoutResult.size.height / 2,
                                ),
                            )
                        }
                    }

                    if (labelConfig.showXLabel) {
                        displayData.fastForEachIndexed { index, stackBarData ->
                            val gap = size.width / (displayData.size * 10)
                            val textLayoutResult = textMeasurer.measure(
                                text = stackBarData.label,
                                style = TextStyle(fontSize = (barWidth / 4).toSp()),
                                overflow = TextOverflow.Clip,
                                maxLines = 1,
                            )
                            drawText(
                                textLayoutResult = textLayoutResult,
                                brush = Brush.linearGradient(labelConfig.textColor.value),
                                topLeft = Offset(
                                    x = (index + 1) * (barWidth + gap) - barWidth / 2 - textLayoutResult.size.width / 2,
                                    y = size.height + 5,
                                ),
                            )
                        }
                    }
                }
            }
            .then(
                if (showAxisLines) {
                    Modifier.drawAxisLineForVerticalChart(
                        hasNegativeValues = false,
                        axisLineColor = barChartColorConfig.axisLineColor
                    )
                } else {
                    Modifier
                }
            )
            .pointerInput(Unit) { detectTapGestures { onBarClick(it) } }
    ) {
        content(size.width, size.height)
    }
}
