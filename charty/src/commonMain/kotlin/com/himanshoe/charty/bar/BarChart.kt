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
fun BarChart(
    data: List<BarData>,
    modifier: Modifier = Modifier,
    barChartConfig: BarChartConfig = BarChartConfig.default(),
    labelConfig: LabelConfig = LabelConfig.default(),
    barChartColorConfig: BarChartColorConfig = BarChartColorConfig.default(),
    onBarClick: (Int, BarData) -> Unit = { _, _ -> },
) {
    BarChartContent(
        data = data,
        modifier = modifier,
        barChartConfig = barChartConfig,
        labelConfig = labelConfig,
        barChartColorConfig = barChartColorConfig,
        onBarClick = onBarClick
    )
}

@Composable
private fun BarChartContent(
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
    ) { canvasHeight, gap, barWidth ->

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
            val color = if (barData.barColor == Color.Unspecified) {
                if (barData.yValue < 0) {
                    barChartColorConfig.negativeGradientBarColors
                } else {
                    barChartColorConfig.defaultGradientBarColors
                }
            } else {
                listOf(barData.barColor, barData.barColor)
            }
            val individualBarTopLeft = Offset(
                x = index * (barWidth + gap) - if (clickedBarIndex == index) (barWidth * 0.02F) / 2 else 0f,
                y = if (barData.yValue < 0) {
                    topLeftY
                } else {
                    topLeftY - if (clickedBarIndex == index) (height.absoluteValue * 0.02F / (if (canDrawNegativeChart) 2 else 1)) else 0f
                }
            )
            val individualBarRectSize = Size(
                width = if (clickedBarIndex == index) barWidth * 1.02F else barWidth,
                height = if (clickedBarIndex == index) height.absoluteValue * 1.02F / (if (canDrawNegativeChart) 2 else 1) else height.absoluteValue / (if (canDrawNegativeChart) 2 else 1)
            )
            val cornerRadius = if (barChartConfig.showCurvedBar) {
                CornerRadius(x = barWidth / 2, y = barWidth / 2)
            } else {
                CornerRadius.Zero
            }
            val textCharCount = if (barData.xValue.toString().length >= 3) {
                if (displayData.count() <= 7) 3 else 1
            } else {
                1
            }
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
                    gap = gap,
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
                require(barData.xValue.toString().isNotEmpty()) {
                    "X value should not be empty"
                }
                drawText(
                    textLayoutResult = textLayoutResult,
                    brush = SolidColor(labelConfig.textColor),
                    topLeft = Offset(
                        x = individualBarTopLeft.x + barWidth / 2 - textLayoutResult.size.width / 2,
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
    gap: Float,
    backgroundTopLeftY: Float,
    canDrawNegativeChart: Boolean,
    maxHeight: Float,
    cornerRadius: CornerRadius,
) {
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

internal fun getDisplayData(
    data: List<BarData>,
    minimumBarCount: Int,
): List<BarData> =
    if (data.size < minimumBarCount) {
        // Add empty bar
        List(minimumBarCount - data.size) { BarData(0F, 0f, Color.Unspecified) } + data
    } else {
        data
    }

private fun isClickInsideBar(
    clickOffset: Offset,
    rectTopLeft: Offset,
    rectSize: Size,
): Boolean =
    clickOffset.x in rectTopLeft.x..(rectTopLeft.x + rectSize.width) &&
            clickOffset.y in rectTopLeft.y..(rectTopLeft.y + rectSize.height)

/**
 * A composable function that provides a scaffold for drawing a bar chart canvas.
 *
 * @param modifier The modifier to be applied to the canvas.
 * @param showAxisLines A boolean indicating whether to show axis lines.
 * @param showRangeLines A boolean indicating whether to show range lines.
 * @param canDrawNegativeChart A boolean indicating whether the chart can draw negative values.
 * @param displayDataCount The number of data points to be displayed.
 * @param axisLineColor The color of the axis lines.
 * @param rangeLineColor The color of the range lines.
 * @param onClick A lambda function to handle click events on the canvas.
 * @param content A lambda function that defines the content to be drawn on the canvas.
 */
@Composable
internal fun BarChartCanvasScaffold(
    modifier: Modifier = Modifier,
    showAxisLines: Boolean = false,
    showRangeLines: Boolean = false,
    canDrawNegativeChart: Boolean = false,
    displayDataCount: Int = 0,
    axisLineColor: Color = Color.Black,
    rangeLineColor: Color = Color.Gray,
    onClick: (Offset) -> Unit = {},
    content: DrawScope.(Float, Float, Float) -> Unit = { _, _, _ -> },
) {
    Canvas(
        modifier = modifier
            .then(
                if (showAxisLines) {
                    Modifier.drawAxisLine(
                        hasNegativeValues = canDrawNegativeChart,
                        axisLineColor = axisLineColor,
                    )
                } else {
                    Modifier
                },
            ).then(
                if (showRangeLines) {
                    Modifier.drawRangeLines(
                        hasNegativeValues = canDrawNegativeChart,
                        rangeLineColor = rangeLineColor,
                    )
                } else {
                    Modifier
                },
            ).fillMaxSize()
            .pointerInput(Unit) { detectTapGestures { onClick(it) } },
    ) {
        val (canvasWidth, canvasHeight) = size
        val gap = canvasWidth / (displayDataCount * 10)
        val barWidth = (canvasWidth - gap * (displayDataCount - 1)) / displayDataCount
        content(canvasHeight, gap, barWidth)
    }
}