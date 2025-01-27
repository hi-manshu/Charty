package com.himanshoe.charty.bar

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.bar.config.BarChartColorConfig
import com.himanshoe.charty.bar.config.BarChartConfig
import com.himanshoe.charty.bar.config.TargetConfig
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.common.LabelConfig
import kotlin.math.absoluteValue

@Composable
fun LineBarChart(
    data: List<BarData>,
    modifier: Modifier = Modifier,
    target: Float? = null,
    targetConfig: TargetConfig = TargetConfig.default(),
    barChartConfig: BarChartConfig = BarChartConfig.default(),
    labelConfig: LabelConfig = LabelConfig.default(),
    barChartColorConfig: BarChartColorConfig = BarChartColorConfig.default(),
    onBarClick: (Int, BarData) -> Unit = { _, _ -> },
) {
    LineBarChartContent(
        data = data,
        modifier = modifier,
        barChartConfig = barChartConfig,
        labelConfig = labelConfig,
        targetConfig = targetConfig,
        target = target,
        barChartColorConfig = barChartColorConfig,
        onBarClick = onBarClick
    )
}

@Composable
private fun LineBarChartContent(
    data: List<BarData>,
    modifier: Modifier = Modifier,
    target: Float? = null,
    targetConfig: TargetConfig = TargetConfig.default(),
    barChartConfig: BarChartConfig = BarChartConfig.default(),
    labelConfig: LabelConfig = LabelConfig.default(),
    barChartColorConfig: BarChartColorConfig = BarChartColorConfig.default(),
    onBarClick: (Int, BarData) -> Unit = { _, _ -> },
) {
    val minValue = data.minOfOrNull { it.yValue.absoluteValue } ?: 0f
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
        showRangeLines = barChartConfig.showGridLines,
        axisLineColor = barChartColorConfig.axisLineColor,
        rangeLineColor = barChartColorConfig.gridLineColor,
        canDrawNegativeChart = canDrawNegativeChart,
        onClick = { clickedOffSet = it },
        displayDataCount = displayData.count(),
    ) { canvasHeight, _, barWidth ->
        if (target != null) {
            require(target in minValue..maxValue) { "Target value should be between $minValue and $maxValue" }
            // Calculate the position of the range buffer line
            val targetLineY = if (hasNegativeValues) {
                canvasHeight / 2
            } else {
                canvasHeight
            }
            val targetLineYPosition = targetLineY - (target / maxValue) * targetLineY
            // Draw the target line
            val brush = if (targetConfig.targetLineGradientBarColors.count() == 1) {
                SolidColor(targetConfig.targetLineGradientBarColors.first())
            } else {
                Brush.linearGradient(targetConfig.targetLineGradientBarColors)
            }
            drawLine(
                brush = brush,
                start = Offset(0f, targetLineYPosition),
                end = Offset(size.width, targetLineYPosition),
                strokeWidth = targetConfig.targetWidth,
                pathEffect = targetConfig.pathEffect
            )
        }
        displayData.fastForEachIndexed { index, barData ->
            val color = if (barData.barColor == Color.Unspecified) {
                if (barData.yValue < 0) {
                    barChartColorConfig.negativeGradientBarColors
                } else {
                    barChartColorConfig.defaultGradientBarColors
                }
            } else {
                listOf(barData.barColor, barData.barColor)
            }
            val height = barData.yValue / maxValue * canvasHeight
            val maxHeight = maxValue / maxValue * canvasHeight
            val topLeftY = if (canDrawNegativeChart) {
                val yAxis = canvasHeight / 2
                if (barData.yValue < 0) {
                    yAxis
                } else {
                    yAxis - height / 2
                }
            } else {
                canvasHeight - height
            }
            val backgroundTopLeftY = if (canDrawNegativeChart) {
                val yAxis = canvasHeight / 2
                if (barData.yValue < 0) {
                    yAxis
                } else {
                    yAxis - maxHeight / 2
                }
            } else {
                canvasHeight - maxHeight
            }

            val individualBarTopLeft = Offset(
                x = index * barWidth + (barWidth - barWidth / 3) / 2, // Center the line within the bar width
                y = if (barData.yValue < 0) {
                    topLeftY
                } else {
                    topLeftY - if (clickedBarIndex == index) {
                        (height.absoluteValue * 0.02F / (if (canDrawNegativeChart) 2 else 1))
                    } else {
                        0f
                    }
                }
            )

            val individualBarRectSize = Size(
                width = barWidth / 3, // Set the width to one-third of the bar width
                height = if (clickedBarIndex == index) {
                    height.absoluteValue * 1.02F / (if (canDrawNegativeChart) 2 else 1)
                } else {
                    height.absoluteValue / (if (canDrawNegativeChart) 2 else 1)
                }
            )
            val cornerRadius = if (barChartConfig.showCurvedBar) {
                CornerRadius(
                    x = barWidth / 2,
                    y = barWidth / 2,
                )
            } else {
                CornerRadius.Zero
            }
            val textCharCount = if (displayData.count() <= 7) 3 else 1
            val textSizeFactor = if (displayData.count() <= 13) 4 else 2
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
