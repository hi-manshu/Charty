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
import com.himanshoe.charty.bar.config.BarChartColorConfig
import com.himanshoe.charty.bar.config.BarChartConfig
import com.himanshoe.charty.bar.config.TargetConfig
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.common.drawAxisLineForVerticalChart
import com.himanshoe.charty.common.drawRangeLinesForVerticalChart
import com.himanshoe.charty.common.drawYAxisLabel
import kotlin.math.absoluteValue

/**
 * A composable function that displays a bar chart.
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
fun BarChart(
    data: () -> List<BarData>,
    modifier: Modifier = Modifier,
    target: Float? = null,
    targetConfig: TargetConfig = TargetConfig.default(),
    barChartConfig: BarChartConfig = BarChartConfig.default(),
    labelConfig: LabelConfig = LabelConfig.default(),
    barChartColorConfig: BarChartColorConfig = BarChartColorConfig.default(),
    onBarClick: (Int, BarData) -> Unit = { _, _ -> },
) {
    BarChartContent(
        data = data,
        modifier = modifier,
        target = target,
        targetConfig = targetConfig,
        barChartConfig = barChartConfig,
        labelConfig = labelConfig,
        barChartColorConfig = barChartColorConfig,
        onBarClick = onBarClick
    )
}

@Composable
private fun BarChartContent(
    data: () -> List<BarData>,
    modifier: Modifier = Modifier,
    target: Float? = null,
    targetConfig: TargetConfig = TargetConfig.default(),
    barChartConfig: BarChartConfig = BarChartConfig.default(),
    labelConfig: LabelConfig = LabelConfig.default(),
    barChartColorConfig: BarChartColorConfig = BarChartColorConfig.default(),
    onBarClick: (Int, BarData) -> Unit = { _, _ -> },
) {
    val maxValue = remember(data()) { data().maxOfOrNull { it.yValue.absoluteValue } ?: 0f }
    val minValue = remember(data()) { data().minOfOrNull { it.yValue.absoluteValue } ?: 0f }
    val hasNegativeValues = remember(data()) { data().any { it.yValue < 0 } }
    val displayData = remember(data) { getDisplayData(data(), barChartConfig.minimumBarCount) }
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
        labelConfig = labelConfig,
        onClick = { clickedOffset = it },
        data = { displayData },
    ) { canvasHeight, gap, barWidth ->

        target?.let {
            require(it in minValue..maxValue) { "Target value should be between $minValue and $maxValue" }
            val targetLineY = if (hasNegativeValues) canvasHeight / 2 else canvasHeight
            val targetLineYPosition = targetLineY - (it / maxValue) * targetLineY
            val brush = Brush.linearGradient(targetConfig.targetLineBarColors.value)
            drawLine(
                brush = brush,
                start = Offset(0f, targetLineYPosition),
                end = Offset(size.width, targetLineYPosition),
                strokeWidth = targetConfig.targetStrokeWidth,
                pathEffect = targetConfig.pathEffect
            )
        }

        displayData.fastForEachIndexed { index, barData ->
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
            val color = if (barData.barColor == Color.Unspecified) {
                if (barData.yValue < 0) barChartColorConfig.negativeGradientBarColors else barChartColorConfig.fillGradientColors
            } else {
                listOf(barData.barColor, barData.barColor)
            }
            val individualBarTopLeft = Offset(
                x = index * (barWidth + gap) - if (clickedBarIndex == index) (barWidth * 0.02F) / 2 else 0f,
                y = if (barData.yValue < 0) topLeftY else topLeftY - if (clickedBarIndex == index) (height.absoluteValue * 0.02F / (if (canDrawNegativeChart) 2 else 1)) else 0f
            )
            val individualBarRectSize = Size(
                width = if (clickedBarIndex == index) barWidth * 1.02F else barWidth,
                height = if (clickedBarIndex == index) height.absoluteValue * 1.02F / (if (canDrawNegativeChart) 2 else 1) else height.absoluteValue / (if (canDrawNegativeChart) 2 else 1)
            )
            val cornerRadius = if (barChartConfig.showCurvedBar) CornerRadius(
                barWidth / 2,
                barWidth / 2
            ) else CornerRadius.Zero
            val textCharCount =
                if (barData.xValue.toString().length >= 3) if (displayData.count() <= 7) 3 else 1 else 1
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
            val brush = Brush.linearGradient(
                colors = color,
                start = Offset(individualBarTopLeft.x, individualBarTopLeft.y),
                end = Offset(
                    individualBarTopLeft.x + individualBarRectSize.width,
                    individualBarTopLeft.y + individualBarRectSize.height
                )
            )
            drawPath(path = path, brush = brush)
            if (labelConfig.showXLabel) {
                require(barData.xValue.toString().isNotEmpty()) { "X value should not be empty" }
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
        List(minimumBarCount - data.size) { BarData(0F, " ", Color.Unspecified) } + data
    } else {
        data
    }

internal fun isClickInsideBar(
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
    axisLineColor: Color = Color.Black,
    labelConfig: LabelConfig = LabelConfig.default(),
    rangeLineColor: Color = Color.Gray,
    data: () -> List<BarData> = { emptyList() },
    onClick: (Offset) -> Unit = {},
    content: DrawScope.(Float, Float, Float) -> Unit = { _, _, _ -> },
) {
    val textMeasurer = rememberTextMeasurer()
    // Calculate the range for labels from the data
    val maxValue = data().maxOfOrNull { it.yValue } ?: 0f
    val minValue = if (canDrawNegativeChart) data().minOfOrNull { it.yValue } ?: 0f else 0f
    val step = (maxValue - minValue) / 4

    Canvas(
        modifier = modifier
            .then(
                if (showAxisLines) {
                    Modifier.drawAxisLineForVerticalChart(
                        hasNegativeValues = canDrawNegativeChart,
                        axisLineColor = axisLineColor,
                    )
                } else {
                    Modifier
                },
            )
            .then(
                if (labelConfig.showYLabel) {
                    Modifier.drawYAxisLabel(
                        minValue = minValue,
                        step = step,
                        maxValue = maxValue,
                        labelColor = labelConfig.textColor,
                        textMeasurer = textMeasurer,
                        count = data().count()
                    )
                } else {
                    Modifier
                },
            ).then(
                if (showRangeLines) {
                    Modifier.drawRangeLinesForVerticalChart(
                        hasNegativeValues = canDrawNegativeChart,
                        rangeLineColor = rangeLineColor,
                    )
                } else {
                    Modifier
                },
            )
            .fillMaxSize()
            .pointerInput(Unit) { detectTapGestures { onClick(it) } },
    ) {
        val (canvasWidth, canvasHeight) = size
        val gap = canvasWidth / (data().count() * 10)
        val barWidth = (canvasWidth - gap * (data().count() - 1)) / data().count()

        content(
            canvasHeight,
            gap,
            barWidth
        )
    }
}
