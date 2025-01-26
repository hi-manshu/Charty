package com.himanshoe.charty.bar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.common.drawAxisLine
import com.himanshoe.charty.common.drawRangeLines
import kotlin.math.absoluteValue

@Composable
fun LineBarChart(
    data: List<BarData>,
    modifier: Modifier = Modifier,
    barChartConfig: BarChartConfig = BarChartConfig.default(),
    labelConfig: LabelConfig = LabelConfig.default(),
    onBarClick: (Int, BarData) -> Unit = { _, _ -> },
) {
    val maxValue = data.maxOfOrNull { it.yValue.absoluteValue } ?: 0f
    val hasNegativeValues = data.any { it.yValue < 0 }
    val displayData = getDisplayData(data = data, minimumBarCount = barChartConfig.minimumBarCount)
    val canDrawNegativeChart = hasNegativeValues && barChartConfig.drawNegativeValueChart
    var clickedOffSet by mutableStateOf(Offset.Zero)
    var clickedBarIndex by mutableIntStateOf(-1)
    val textMeasurer = rememberTextMeasurer()
    val bottomPadding = if (labelConfig.showLabel && !hasNegativeValues) 8.dp else 0.dp

    BarChartCanvasScaffold(
        modifier = modifier.padding(bottom = bottomPadding),
        showAxisLines = barChartConfig.showAxisLines,
        showRangeLines = barChartConfig.showRangeLines,
        canDrawNegativeChart = canDrawNegativeChart,
        displayDataCount = displayData.count(),
        onClick = { clickedOffSet = it },
        content = { canvasHeight, gap, barWidth ->
            displayData.fastForEachIndexed { index, barData ->
                val height = barData.yValue / maxValue * canvasHeight
                val maxHeight = maxValue / maxValue * canvasHeight
                val topLeftY = if (canDrawNegativeChart) {
                    val yAxis = canvasHeight / 2
                    if (barData.yValue < 0) yAxis else yAxis - height / 2
                } else {
                    canvasHeight - height
                }
                val backgroundTopLeftY = if (canDrawNegativeChart) {
                    val yAxis = canvasHeight / 2
                    if (barData.yValue < 0) yAxis else yAxis - maxHeight / 2
                } else {
                    canvasHeight - maxHeight
                }
                val individualBarTopLeft = Offset(
                    x = index * (barWidth + gap) - if (clickedBarIndex == index) (barWidth * 0.02F) / 2 else 0f,
                    y = if (barData.yValue < 0) {
                        topLeftY
                    } else {
                        topLeftY - if (clickedBarIndex == index) {
                            (height.absoluteValue * 0.02F / (if (canDrawNegativeChart) 2 else 1))
                        } else {
                            0f
                        }
                    },
                )
                val individualBarRectSize = Size(
                    width = if (clickedBarIndex == index) barWidth * 1.02F else barWidth,
                    height = if (clickedBarIndex == index) {
                        height.absoluteValue * 1.02F / (if (canDrawNegativeChart) 2 else 1)
                    } else {
                        height.absoluteValue / (if (canDrawNegativeChart) 2 else 1)
                    },
                )

                if (isClickInsideBar(clickedOffSet, individualBarTopLeft, individualBarRectSize)) {
                    clickedBarIndex = index
                    onBarClick(index, barData)
                }
                val cornerRadius = if (barChartConfig.showCurvedBar) {
                    CornerRadius(
                        x = barWidth / 4,
                        y = barWidth / 4,
                    )
                } else {
                    CornerRadius.Zero
                }
                val textCharCount = if (displayData.count() <= 7) 3 else 1
                val textSizeFactor = if (displayData.count() <= 13) 4 else 2
                val textLayoutResult = textMeasurer.measure(
                    text = barData.xValue.toString().take(textCharCount),
                    style = TextStyle(
                        fontSize = (barWidth / textSizeFactor).toSp(),
                    ),
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                )

                val textOffsetY = if (barData.yValue < 0) {
                    individualBarTopLeft.y - textLayoutResult.size.height - 5
                } else {
                    individualBarTopLeft.y + individualBarRectSize.height + 5
                }

                // Draw background bar only if its value is greater than 0
                if (barData.yValue != 0F) {
                    drawRoundRect(
                        color = barData.barBackgroundColor,
                        topLeft = Offset(x = index * (barWidth + gap), y = backgroundTopLeftY),
                        size = Size(
                            width = barWidth,
                            height = if (canDrawNegativeChart) maxHeight.absoluteValue / 2 else maxHeight.absoluteValue,
                        ),
                        cornerRadius = cornerRadius,
                    )
                }
                drawRoundRect(
                    color = barData.barColor,
                    topLeft = individualBarTopLeft,
                    size = individualBarRectSize,
                    cornerRadius = cornerRadius,
                )
                if (labelConfig.showLabel) {
                    drawText(
                        textLayoutResult = textLayoutResult,
                        brush = SolidColor(labelConfig.textColor),
                        topLeft = Offset(
                            individualBarTopLeft.x + barWidth / 2 - textLayoutResult.size.width / 2,
                            textOffsetY,
                        ),
                    )
                }
            }
        },
    )
}

@Composable
fun LineBarChart(
    data: List<BarData>,
    modifier: Modifier = Modifier,
    barChartConfig: BarChartConfig = BarChartConfig.default(),
    labelConfig: LabelConfig = LabelConfig.default(),
    barChartColorConfig: BarChartColorConfig = BarChartColorConfig.default(),
    onBarClick: (Int, BarData) -> Unit = { _, _ -> },
) {
    val maxValue = data.maxOfOrNull { it.yValue.absoluteValue } ?: 0f
    val hasNegativeValues = data.any { it.yValue < 0 }
    val displayData = getDisplayData(data = data, minimumBarCount = barChartConfig.minimumBarCount)
    val canDrawNegativeChart = hasNegativeValues && barChartConfig.drawNegativeValueChart
    val textMeasurer = rememberTextMeasurer()
    val bottomPadding = if (labelConfig.showLabel && !hasNegativeValues) 8.dp else 0.dp

    var clickedOffSet by mutableStateOf(Offset.Zero)
    var clickedBarIndex by mutableIntStateOf(-1)

    BarChartCanvasScaffold(
        modifier = modifier.padding(bottom = bottomPadding),
        showAxisLines = barChartConfig.showAxisLines,
        showRangeLines = barChartConfig.showRangeLines,
        axisLineColor = barChartColorConfig.axisLineColor,
        rangeLineColor = barChartColorConfig.rangeLineColor,
        canDrawNegativeChart = canDrawNegativeChart,
        onClick = { clickedOffSet = it },
        displayDataCount = displayData.count(),
    ) { canvasHeight, _, barWidth ->

        displayData.fastForEachIndexed { index, barData ->
            val height = barData.yValue / maxValue * canvasHeight
            val maxHeight = maxValue / maxValue * canvasHeight
            val textCharCount = if (displayData.count() <= 7) 3 else 1
            val textSizeFactor = if (displayData.count() <= 13) 4 else 2
            val topLeftY = if (canDrawNegativeChart) {
                val yAxis = canvasHeight / 2
                if (barData.yValue < 0) yAxis else yAxis - height / 2
            } else {
                canvasHeight - height
            }
            val color = if (barData.barColor == Color.Unspecified) {
                if (barData.yValue < 0) {
                    barChartColorConfig.negativeGradientBarColors
                } else {
                    barChartColorConfig.defaultGradientBarColors
                }
            } else {
                listOf(barData.barColor, barData.barColor)
            }
            val backgroundTopLeftY = if (canDrawNegativeChart) {
                val yAxis = canvasHeight / 2
                if (barData.yValue < 0) yAxis else yAxis - maxHeight / 2
            } else {
                canvasHeight - maxHeight
            }

            val individualBarTopLeft = Offset(
                x = index * barWidth + (barWidth - barWidth / 3) / 2, // Center the line within the bar width
                y = if (barData.yValue < 0) {
                    topLeftY
                } else {
                    topLeftY - if (clickedBarIndex == index) (height.absoluteValue * 0.02F / (if (canDrawNegativeChart) 2 else 1)) else 0f
                }
            )

            val individualBarRectSize = Size(
                width = barWidth / 3, // Set the width to one-third of the bar width
                height = if (clickedBarIndex == index) height.absoluteValue * 1.02F / (if (canDrawNegativeChart) 2 else 1) else height.absoluteValue / (if (canDrawNegativeChart) 2 else 1)
            )
            val cornerRadius = if (barChartConfig.showCurvedBar) {
                CornerRadius(
                    x = barWidth / 4,
                    y = barWidth / 4,
                )
            } else {
                CornerRadius.Zero
            }
            val textLayoutResult = textMeasurer.measure(
                text = barData.xValue.toString().take(textCharCount),
                style = TextStyle(fontSize = (barWidth / textSizeFactor).toSp()),
                overflow = TextOverflow.Clip,
                maxLines = 1,
            )
            val textOffsetY = if (barData.yValue < 0) {
                individualBarTopLeft.y - textLayoutResult.size.height - 5
            } else {
                individualBarTopLeft.y + individualBarRectSize.height + 5
            }

            if (isClickInsideBar(clickedOffSet, individualBarTopLeft, individualBarRectSize)) {
                clickedBarIndex = index
                onBarClick(index, barData)
            }

            // Draw background bar only if its value is greater than 0
            if (barData.yValue != 0F) {
                backgroundColorBar(
                    barData = barData,
                    index = index,
                    barWidth = barWidth,
                    backgroundTopLeftY = backgroundTopLeftY,
                    canDrawNegativeChart = canDrawNegativeChart,
                    maxHeight = maxHeight,
                    cornerRadius = cornerRadius,
                )
            }
            val path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(
                            offset = individualBarTopLeft,
                            size = individualBarRectSize,
                        ),
                        topLeft = if (barData.yValue >= 0) cornerRadius else CornerRadius.Zero,
                        topRight = if (barData.yValue >= 0) cornerRadius else CornerRadius.Zero,
                        bottomLeft = if (barData.yValue < 0) cornerRadius else CornerRadius.Zero,
                        bottomRight = if (barData.yValue < 0) cornerRadius else CornerRadius.Zero

                    )
                )
            }
            drawPath(path = path, brush = Brush.linearGradient(color))
            if (labelConfig.showLabel) {
                drawText(
                    textLayoutResult = textLayoutResult,
                    brush = SolidColor(labelConfig.textColor),
                    topLeft = Offset(
                        x = individualBarTopLeft.x + individualBarRectSize.width / 2 - textLayoutResult.size.width / 2, // Center the text within the bar width
                        y = textOffsetY,
                    ),
                )
            }
        }
    }
}

private fun DrawScope.backgroundColorBar(
    barData: BarData,
    index: Int,
    barWidth: Float,
    backgroundTopLeftY: Float,
    canDrawNegativeChart: Boolean,
    maxHeight: Float,
    cornerRadius: CornerRadius,
) {
    drawRoundRect(
        color = barData.barBackgroundColor,
        topLeft = Offset(
            x = index * (barWidth) + (barWidth - barWidth / 3) / 2, // Center the background bar within the bar width
            y = backgroundTopLeftY
        ),
        size = Size(
            width = barWidth / 3, // Set the width to one-third of the bar width
            height = if (canDrawNegativeChart) maxHeight.absoluteValue / 2 else maxHeight.absoluteValue
        ),
        cornerRadius = cornerRadius,
    )
}

private fun isClickInsideBar(
    clickOffset: Offset,
    rectTopLeft: Offset,
    rectSize: Size,
): Boolean =
    clickOffset.x in rectTopLeft.x..(rectTopLeft.x + rectSize.width) && clickOffset.y in rectTopLeft.y..(rectTopLeft.y + rectSize.height)


