package com.himanshoe.charty.bar

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

/**
 * A composable function that displays a line bar chart.
 *
 * @param data A lambda function that returns a list of `BarData` representing the data points for the bar chart.
 * @param modifier A `Modifier` for customizing the layout or drawing behavior of the chart.
 * @param target An optional target value to be displayed on the chart.
 * @param targetConfig A `TargetConfig` object for configuring the appearance of the target line.
 * @param barChartConfig A `BarChartConfig` object for configuring the chart's appearance and behavior.
 * @param labelConfig A `LabelConfig` object for configuring the labels on the chart.
 * @param barChartColorConfig A `BarChartColorConfig` object for configuring the colors of the bars, axis lines, and grid lines.
 * @param onBarClick A lambda function to handle click events on the bars. It receives the index of the clicked bar and the corresponding `BarData` as parameters.
 */
@Composable
fun LineBarChart(
    data: () -> List<BarData>,
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
    data: () -> List<BarData>,
    modifier: Modifier = Modifier,
    target: Float? = null,
    targetConfig: TargetConfig = TargetConfig.default(),
    barChartConfig: BarChartConfig = BarChartConfig.default(),
    labelConfig: LabelConfig = LabelConfig.default(),
    barChartColorConfig: BarChartColorConfig = BarChartColorConfig.default(),
    onBarClick: (Int, BarData) -> Unit = { _, _ -> },
) {
    val minValue = data().minOfOrNull { it.yValue.absoluteValue } ?: 0f
    val maxValue = data().maxOfOrNull { it.yValue.absoluteValue } ?: 0f
    val hasNegativeValues = data().any { it.yValue < 0 }
    val displayData = remember(data()) { getDisplayData(data(), barChartConfig.minimumBarCount) }
    val canDrawNegativeChart = hasNegativeValues && barChartConfig.drawNegativeValueChart
    val textMeasurer = rememberTextMeasurer()
    val bottomPadding = if (labelConfig.showXLabel && !hasNegativeValues) 8.dp else 0.dp
    val leftPadding = if (labelConfig.showYLabel) 24.dp else 0.dp

    var clickedOffset by mutableStateOf(Offset.Zero)
    var clickedBarIndex by mutableIntStateOf(-1)

    BarChartCanvasScaffold(
        modifier = modifier.padding(bottom = bottomPadding, start = leftPadding),
        showAxisLines = barChartConfig.showAxisLines,
        showRangeLines = barChartConfig.showGridLines,
        axisLineColor = barChartColorConfig.axisLineColor,
        rangeLineColor = barChartColorConfig.gridLineColor,
        canDrawNegativeChart = canDrawNegativeChart,
        onClick = { clickedOffset = it },
        data = data
    ) { canvasHeight, _, barWidth ->
        target?.let {
            require(it in minValue..maxValue) { "Target value should be between $minValue and $maxValue" }
            val targetLineY = if (hasNegativeValues) canvasHeight / 2 else canvasHeight
            val targetLineYPosition = targetLineY - (it / maxValue) * targetLineY
            val brush =  Brush.linearGradient(targetConfig.targetLineBarColors.value)
            drawLine(
                brush = brush,
                start = Offset(0f, targetLineYPosition),
                end = Offset(size.width, targetLineYPosition),
                strokeWidth = targetConfig.targetStrokeWidth,
                pathEffect = targetConfig.pathEffect
            )
        }

        displayData.fastForEachIndexed { index, barData ->
            val color = if (barData.barColor == Color.Unspecified) {
                if (barData.yValue < 0) barChartColorConfig.negativeGradientBarColors else barChartColorConfig.fillGradientColors
            } else {
                listOf(barData.barColor, barData.barColor)
            }
            val height = barData.yValue / maxValue * canvasHeight
            val maxHeight = maxValue / maxValue * canvasHeight
            val yAxis = canvasHeight / 2
            val topLeftY = if (canDrawNegativeChart) {
                if (barData.yValue < 0) yAxis else yAxis - height / 2
            } else {
                canvasHeight - height
            }
            val backgroundTopLeftY = if (canDrawNegativeChart) {
                if (barData.yValue < 0) yAxis else yAxis - maxHeight / 2
            } else {
                canvasHeight - maxHeight
            }

            val individualBarTopLeft = Offset(
                x = index * barWidth + (barWidth - barWidth / 3) / 2,
                y = if (barData.yValue < 0) topLeftY else topLeftY - if (clickedBarIndex == index) (height.absoluteValue * 0.02F / (if (canDrawNegativeChart) 2 else 1)) else 0f
            )

            val individualBarRectSize = Size(
                width = barWidth / 3,
                height = if (clickedBarIndex == index) height.absoluteValue * 1.02F / (if (canDrawNegativeChart) 2 else 1) else height.absoluteValue / (if (canDrawNegativeChart) 2 else 1)
            )
            val cornerRadius = if (barChartConfig.showCurvedBar) CornerRadius(
                barWidth / 2,
                barWidth / 2
            ) else CornerRadius.Zero
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

            if (isClickInsideBar(clickedOffset, individualBarTopLeft, individualBarRectSize)) {
                clickedBarIndex = index
                onBarClick(index, barData)
            }

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
            if (labelConfig.showXLabel) {
                drawText(
                    textLayoutResult = textLayoutResult,
                    brush = SolidColor(labelConfig.textColor),
                    topLeft = Offset(
                        x = individualBarTopLeft.x + individualBarRectSize.width / 2 - textLayoutResult.size.width / 2,
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
            x = index * barWidth + (barWidth - barWidth / 3) / 2,
            y = backgroundTopLeftY
        ),
        size = Size(
            width = barWidth / 3,
            height = if (canDrawNegativeChart) maxHeight.absoluteValue / 2 else maxHeight.absoluteValue
        ),
        cornerRadius = cornerRadius,
    )
}