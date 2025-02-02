package com.himanshoe.charty.bar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.bar.config.ComparisonBarChartConfig
import com.himanshoe.charty.bar.model.ComparisonBarData
import com.himanshoe.charty.bar.modifier.drawAxesAndGridLines
import com.himanshoe.charty.common.LabelConfig

/**
 * A composable function that displays a comparison bar chart.
 *
 * @param data A lambda function that returns a list of `ComparisonBarData` representing the data points for the comparison bar chart.
 * @param modifier A `Modifier` for customizing the layout or drawing behavior of the chart.
 * @param labelConfig A `LabelConfig` object for configuring the labels on the chart.
 * @param comparisonBarChartConfig A `ComparisonBarChartConfig` object for configuring the chart's appearance and behavior.
 * @param onBarClick A lambda function to handle click events on the bars. It receives the index of the clicked bar as a parameter.
 */
@Composable
fun ComparisonBarChart(
    data: () -> List<ComparisonBarData>,
    modifier: Modifier = Modifier,
    labelConfig: LabelConfig = LabelConfig.default(),
    comparisonBarChartConfig: ComparisonBarChartConfig = ComparisonBarChartConfig.default(),
    onBarClick: (Int) -> Unit = {}
) {
    ComparisonBarContent(
        labelConfig = labelConfig,
        modifier = modifier,
        data = data,
        comparisonBarChartConfig = comparisonBarChartConfig,
        onBarClick = onBarClick
    )
}

@Composable
private fun ComparisonBarContent(
    data: () -> List<ComparisonBarData>,
    labelConfig: LabelConfig,
    comparisonBarChartConfig: ComparisonBarChartConfig,
    modifier: Modifier = Modifier,
    onBarClick: (Int) -> Unit = {}
) {
    var selectedCategory by remember { mutableStateOf(-1) }
    val textMeasurer = rememberTextMeasurer()
    val bottomPadding = if (labelConfig.showXLabel) 24.dp else 0.dp
    val leftPadding = if (labelConfig.showYLabel) 24.dp else 0.dp

    val dataList = data()
    val maxValue = dataList.flatMap { it.bars }.maxOf { it }
    val groupCount = dataList.size
    val maxBarsCount = dataList.maxOf { it.bars.size }

    Canvas(modifier = modifier
        .padding(bottom = bottomPadding, start = leftPadding)
        .fillMaxSize()
        .drawAxesAndGridLines(
            maxValue = maxValue,
            step = maxValue / 4,
            textMeasurer = textMeasurer,
            labelConfig = labelConfig,
            showAxisLines = comparisonBarChartConfig.showAxisLines,
            showGridLines = comparisonBarChartConfig.showGridLines
        )
        .pointerInput(Unit) {
            detectTapGestures { offset ->
                val groupWidth = size.width / groupCount
                val clickedGroupIndex = (offset.x / groupWidth).toInt()
                if (clickedGroupIndex in dataList.indices) {
                    selectedCategory = clickedGroupIndex
                    onBarClick(clickedGroupIndex)
                }
            }
        }
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val groupWidth = canvasWidth / groupCount
        val barWidth = groupWidth / (maxBarsCount * 2)
        val cornerRadius = if (comparisonBarChartConfig.showCurvedBar) CornerRadius(
            barWidth / 2, barWidth / 2
        ) else CornerRadius.Zero

        dataList.fastForEachIndexed { groupIndex, group ->
            val groupStartX = groupIndex * groupWidth
            if (groupIndex == selectedCategory) {
                drawBar(
                    cornerRadius = cornerRadius,
                    x = groupStartX,
                    y = 0f,
                    width = groupWidth,
                    height = canvasHeight,
                    brush = SolidColor(Color.Gray.copy(alpha = 0.1F))
                )
            }
            group.bars.fastForEachIndexed { barIndex, barValue ->
                val barHeight = (barValue / maxValue) * canvasHeight
                val barX =
                    groupStartX + (groupWidth - (group.bars.size * (barWidth + barWidth / 4))) / 2 + barIndex * (barWidth + barWidth / 4)
                val brush = Brush.linearGradient(
                    colors = group.colors[barIndex].value,
                    start = Offset(barX, canvasHeight - barHeight),
                    end = Offset(barX + barWidth, canvasHeight)
                )

                drawBar(
                    x = barX,
                    y = canvasHeight - barHeight,
                    width = barWidth,
                    height = barHeight,
                    brush = brush,
                    cornerRadius = cornerRadius
                )
            }

            for (barIndex in group.bars.size until maxBarsCount) {
                val barX =
                    groupStartX + (groupWidth - (group.bars.size * (barWidth + barWidth / 4))) / 2 + barIndex * (barWidth + barWidth / 4)
                drawBar(
                    x = barX,
                    y = canvasHeight,
                    width = barWidth,
                    height = 0f,
                    brush = SolidColor(Color.Transparent),
                    cornerRadius = cornerRadius
                )
            }

            val textCharCount = if (groupCount <= 7) 3 else 1
            val textSizeFactor = groupCount * if (groupCount <= 5) 20 else 40
            val textLayoutResult = textMeasurer.measure(
                text = group.label.take(textCharCount),
                style = TextStyle(fontSize = (canvasWidth / textSizeFactor).sp)
            )
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(
                    x = groupStartX + groupWidth / 2 - textLayoutResult.size.width / 2,
                    y = canvasHeight + (canvasHeight * 0.01f)
                ),
                brush = SolidColor(Color.Black)
            )
        }
    }
}

private fun DrawScope.drawBar(
    x: Float, y: Float, width: Float, height: Float, brush: Brush, cornerRadius: CornerRadius
) {
    val path = Path().apply {
        addRoundRect(
            RoundRect(
                rect = Rect(Offset(x, y), Size(width, height)),
                topLeft = cornerRadius,
                topRight = cornerRadius,
                bottomLeft = CornerRadius.Zero,
                bottomRight = CornerRadius.Zero
            )
        )
    }
    drawPath(path = path, brush = brush)
}