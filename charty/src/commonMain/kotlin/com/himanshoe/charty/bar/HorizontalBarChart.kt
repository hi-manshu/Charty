package com.himanshoe.charty.bar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.bar.config.BarChartColorConfig
import com.himanshoe.charty.bar.config.BarChartConfig
import com.himanshoe.charty.bar.config.HorizontalBarLabelConfig
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.common.drawRangeLineForHorizontalChart
import com.himanshoe.charty.common.drawYAxisLineForHorizontalChart
import kotlin.math.absoluteValue

/**
 * A composable function that displays a horizontal bar chart.
 *
 * @param data The list of `BarData` objects representing the data to be displayed in the chart.
 * @param modifier The modifier to be applied to the chart.
 * @param barChartConfig The configuration for the bar chart, including settings like minimum bar count, axis lines, and grid lines.
 * @param barChartColorConfig The color configuration for the bar chart, including colors for the bars, axis lines, and grid lines.
 * @param horizontalBarLabelConfig The configuration for the labels on the horizontal bars, including text colors and background colors.
 * @param onBarClick A lambda function to handle click events on the bars. It receives the clicked `BarData` as a parameter.
 */
@Composable
fun HorizontalBarChart(
    data: () -> List<BarData>,
    modifier: Modifier = Modifier,
    barChartConfig: BarChartConfig = BarChartConfig.default(),
    barChartColorConfig: BarChartColorConfig = BarChartColorConfig.default(),
    horizontalBarLabelConfig: HorizontalBarLabelConfig = HorizontalBarLabelConfig.MultiColorConfig.default(),
    onBarClick: (BarData) -> Unit = {}
) {
    HorizontalBarChartContent(
        data = data,
        modifier = modifier,
        barChartConfig = barChartConfig,
        barChartColorConfig = barChartColorConfig,
        horizontalBarLabelConfig = horizontalBarLabelConfig,
        onBarClick = onBarClick
    )
}

@Composable
private fun HorizontalBarChartContent(
    data: () -> List<BarData>,
    modifier: Modifier = Modifier,
    barChartConfig: BarChartConfig = BarChartConfig.default(),
    barChartColorConfig: BarChartColorConfig = BarChartColorConfig.default(),
    horizontalBarLabelConfig: HorizontalBarLabelConfig = HorizontalBarLabelConfig.MultiColorConfig.default(),
    onBarClick: (BarData) -> Unit = {}
) {
    val maxValue = remember(data()) { data().maxOfOrNull { it.yValue.absoluteValue } ?: 0f }
    val hasNegativeValues = remember(data()) { data().any { it.yValue < 0 } }
    val allNegativeValues = remember(data()) { data().all { it.yValue < 0 } }
    val allPositiveValues = remember(data()) { data().all { it.yValue >= 0 } }

    val displayData = remember(data) { getDisplayData(data(), barChartConfig.minimumBarCount) }
    var clickedOffSet by remember { mutableStateOf<Offset?>(null) }
    var clickedBarIndex by remember { mutableStateOf(-1) }
    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(data) {
                detectDragGestures(
                    onDragStart = { offset -> clickedOffSet = offset },
                    onDrag = { change, _ -> clickedOffSet = change.position },
                    onDragEnd = { clickedOffSet = null }
                )
            }
            .pointerInput(data) {
                detectTapGestures(
                    onPress = { offset ->
                        clickedOffSet = offset
                    }
                )
            }
            .applyAxisAndGridLines(
                barChartConfig = barChartConfig,
                allNegativeValues = allNegativeValues,
                gridLineColor = barChartColorConfig.gridLineColor,
                axisLineColor = barChartColorConfig.axisLineColor,
                allPositiveValues = allPositiveValues,
                hasNegativeValues = hasNegativeValues,
            )
    ) {
        val barHeight = size.height / displayData.size
        val gap = size.width / (displayData.size * 50)
        val centerX = if (hasNegativeValues && !allNegativeValues) size.width / 2 else 0f

        displayData.fastForEachIndexed { index, barData ->
            val (barWidth, additionalWidth, topLeftX, topLeftY) = calculateBarDimensions(
                barData = barData,
                maxValue = maxValue,
                centerX = centerX,
                barHeight = barHeight,
                gap = gap,
                clickedBarIndex = clickedBarIndex,
                index = index,
                size = size,
                allNegativeValues = allNegativeValues
            )
            val color = getBarColor(barData = barData, barChartColorConfig = barChartColorConfig)
            val barSize = Size(width = barWidth + additionalWidth, height = barHeight - gap)

            val brush = Brush.linearGradient(
                colors = color,
                start = Offset(topLeftX, topLeftY),
                end = Offset(topLeftX + barSize.width, topLeftY + barSize.height)
            )

            drawBar(
                topLeft = Offset(topLeftX, topLeftY),
                size = barSize,
                brush = brush,
                showCurvedBar = barChartConfig.showCurvedBar,
                isNegative = barData.yValue < 0,
                allNegativeValues = allNegativeValues,
                allPositiveValues = allPositiveValues
            )

            if ((horizontalBarLabelConfig.showLabel || clickedBarIndex == index) && barData.xValue.toString()
                    .isNotEmpty() && barWidth > 0
            ) {
                drawLabel(
                    barData = barData,
                    textMeasurer = textMeasurer,
                    barWidth = barWidth,
                    topLeftX = topLeftX,
                    topLeftY = topLeftY,
                    barHeight = barHeight,
                    gap = gap,
                    horizontalBarLabelConfig = horizontalBarLabelConfig,
                    allNegativeValues = allNegativeValues,
                    allPositiveValues = allPositiveValues
                )
            }

            clickedOffSet?.let {
                if (it.x in topLeftX..(topLeftX + barWidth) && it.y in topLeftY..(topLeftY + barHeight - gap)) {
                    clickedBarIndex = index
                    onBarClick(barData)
                }
            }
        }
    }
}

private fun DrawScope.drawBar(
    topLeft: Offset,
    size: Size,
    brush: Brush,
    isNegative: Boolean,
    allNegativeValues: Boolean,
    allPositiveValues: Boolean,
    showCurvedBar: Boolean,
) {
    val cornerRadius = if (showCurvedBar) {
        CornerRadius(
            x = size.height / 2, y = size.height / 2
        )
    } else {
        CornerRadius.Zero
    }
    val path = Path().apply {
        addRoundRect(
            RoundRect(
                rect = Rect(topLeft, size),
                topLeft = if (isNegative && !allNegativeValues) cornerRadius else CornerRadius.Zero,
                bottomLeft = if (isNegative && !allNegativeValues) cornerRadius else CornerRadius.Zero,
                topRight = if (!isNegative || allPositiveValues || allNegativeValues) cornerRadius else CornerRadius.Zero,
                bottomRight = if (!isNegative || allPositiveValues || allNegativeValues) cornerRadius else CornerRadius.Zero
            )
        )
    }
    drawPath(path = path, brush = brush)
}

private fun calculateBarDimensions(
    barData: BarData, maxValue: Float, centerX: Float, barHeight: Float, gap: Float,
    clickedBarIndex: Int, index: Int, size: Size, allNegativeValues: Boolean
): BarDimensions {
    val barWidth = (barData.yValue.absoluteValue / maxValue) * (size.width / 2)
    val additionalWidth = if (clickedBarIndex == index) barWidth * 0.02F else 0F
    val topLeftX =
        if (barData.yValue < 0 && !allNegativeValues) centerX - barWidth - additionalWidth else centerX
    val topLeftY = index * barHeight + gap * index
    return BarDimensions(barWidth, additionalWidth, topLeftX, topLeftY)
}

private fun getBarColor(barData: BarData, barChartColorConfig: BarChartColorConfig): List<Color> {
    return if (barData.barColor == Color.Unspecified) {
        if (barData.yValue < 0) {
            barChartColorConfig.negativeGradientBarColors
        } else {
            barChartColorConfig.fillGradientColors
        }
    } else {
        listOf(barData.barColor, barData.barColor)
    }
}

private fun DrawScope.drawLabel(
    barData: BarData, textMeasurer: TextMeasurer, barWidth: Float, topLeftX: Float, topLeftY: Float,
    barHeight: Float, gap: Float, horizontalBarLabelConfig: HorizontalBarLabelConfig,
    allNegativeValues: Boolean, allPositiveValues: Boolean
) {
    val textLayoutResult = textMeasurer.measure(
        text = barData.xValue.toString(),
        style = TextStyle(fontSize = 12.sp),
        overflow = TextOverflow.Clip,
        maxLines = 1,
    )
    val textX = if (barData.yValue < 0 && !allNegativeValues) {
        topLeftX - textLayoutResult.size.width / 2
    } else {
        topLeftX + barWidth - textLayoutResult.size.width / 2
    }
    val textY = topLeftY + barHeight / 2 - textLayoutResult.size.height / 2

    val rotationAngle = if (horizontalBarLabelConfig.hasOverlappingLabel) {
        0F
    } else {
        when {
            allNegativeValues || allPositiveValues -> 90f
            barData.yValue < 0 -> -90f
            else -> 90f
        }
    }

    val backgroundRect = Rect(
        offset = Offset(textX - gap * 5, textY - gap),
        size = Size(
            width = textLayoutResult.size.width + 10 * gap,
            height = textLayoutResult.size.height + 2 * gap
        )
    )
    rotate(degrees = rotationAngle, pivot = backgroundRect.center) {
        drawRoundRect(
            brush = Brush.linearGradient(horizontalBarLabelConfig.textBackgroundColors),
            topLeft = backgroundRect.topLeft,
            size = backgroundRect.size,
            cornerRadius = CornerRadius(x = size.height / 2, y = size.height / 2),
        )

        drawText(
            brush = Brush.linearGradient(horizontalBarLabelConfig.textColors),
            textLayoutResult = textLayoutResult,
            topLeft = Offset(textX, textY)
        )
    }
}

private data class BarDimensions(
    val barWidth: Float,
    val additionalWidth: Float,
    val topLeftX: Float,
    val topLeftY: Float
)

private fun Modifier.applyAxisAndGridLines(
    barChartConfig: BarChartConfig,
    allNegativeValues: Boolean,
    allPositiveValues: Boolean,
    hasNegativeValues: Boolean,
    gridLineColor: Color,
    axisLineColor: Color,
): Modifier {
    var modifier = this

    if (barChartConfig.showAxisLines) {
        modifier = modifier.then(
            Modifier.drawYAxisLineForHorizontalChart(
                allNegativeValues = allNegativeValues,
                allPositiveValues = allPositiveValues,
                axisLineColor = axisLineColor,
                hasNegativeValues = hasNegativeValues
            )
        )
    }

    if (barChartConfig.showGridLines) {
        modifier = modifier.then(
            Modifier.drawRangeLineForHorizontalChart(
                allNegativeValues = allNegativeValues,
                allPositiveValues = allPositiveValues,
                axisLineColor = gridLineColor,
            )
        )
    }

    return modifier
}

private fun DrawScope.drawBar(
    topLeft: Offset,
    size: Size,
    colors: List<Color>,
    isNegative: Boolean,
    allNegativeValues: Boolean,
    allPositiveValues: Boolean,
    showCurvedBar: Boolean,
) {
    val cornerRadius = if (showCurvedBar) {
        CornerRadius(
            x = size.height / 2, y = size.height / 2
        )
    } else {
        CornerRadius.Zero
    }
    val path = Path().apply {
        addRoundRect(
            RoundRect(
                rect = Rect(topLeft, size),
                topLeft = if (isNegative && !allNegativeValues) cornerRadius else CornerRadius.Zero,
                bottomLeft = if (isNegative && !allNegativeValues) cornerRadius else CornerRadius.Zero,
                topRight = if (!isNegative || allPositiveValues || allNegativeValues) cornerRadius else CornerRadius.Zero,
                bottomRight = if (!isNegative || allPositiveValues || allNegativeValues) cornerRadius else CornerRadius.Zero
            )
        )
    }
    drawPath(path = path, brush = Brush.linearGradient(colors))
}
