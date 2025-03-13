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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastFlatMap
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.bar.config.ComparisonBarChartConfig
import com.himanshoe.charty.bar.model.ComparisonBarData
import com.himanshoe.charty.bar.modifier.drawAxesAndGridLines
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.common.getDrawingPath
import com.himanshoe.charty.common.getTetStyle
import com.himanshoe.charty.common.getXLabelTextCharCount

/**
 * A composable function that displays a comparison bar chart.
 *
 * @param data A lambda function that returns a list of `ComparisonBarData` representing the data points for the comparison bar chart.
 * @param modifier A `Modifier` for customizing the layout or drawing behavior of the chart.
 * @param labelConfig A `LabelConfig` object for configuring the labels on the chart.
 * @param comparisonBarChartConfig A `ComparisonBarChartConfig` object for configuring the chart's appearance and behavior.
 * @param onGroupClicked A lambda function to handle click events on the bars. It receives the index of the clicked bar as a parameter.
 */
@Composable
fun ComparisonBarChart(
    data: () -> List<ComparisonBarData>,
    modifier: Modifier = Modifier,
    labelConfig: LabelConfig = LabelConfig.default(),
    comparisonBarChartConfig: ComparisonBarChartConfig = ComparisonBarChartConfig.default(),
    onGroupClicked: (Int) -> Unit = {}
) {
    ComparisonBarContent(
        labelConfig = labelConfig,
        modifier = modifier,
        data = data,
        comparisonBarChartConfig = comparisonBarChartConfig,
        onGroupClicked = onGroupClicked
    )
}

@Composable
private fun ComparisonBarContent(
    data: () -> List<ComparisonBarData>,
    labelConfig: LabelConfig,
    comparisonBarChartConfig: ComparisonBarChartConfig,
    modifier: Modifier = Modifier,
    onGroupClicked: (Int) -> Unit = {}
) {
    var selectedCategory by remember { mutableStateOf(-1) }
    val textMeasurer = rememberTextMeasurer()
    val bottomPadding = if (labelConfig.showXLabel) 24.dp else 0.dp
    val leftPadding = if (labelConfig.showYLabel) 24.dp else 0.dp

    val dataList = data()
    val maxValue = remember(dataList) { dataList.fastFlatMap { it.bars }.maxOf { it } }
    val groupCount = dataList.size
    val maxBarsCount = remember(dataList) { dataList.maxOf { it.bars.size } }
    Canvas(
        modifier = modifier
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
                        onGroupClicked(clickedGroupIndex)
                    }
                }
            }
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val groupWidth = canvasWidth / groupCount
        val barWidth = groupWidth / (maxBarsCount * 2)
        val cornerRadius = if (comparisonBarChartConfig.showCurvedBar) {
            CornerRadius(
                x = barWidth / 2,
                y = barWidth / 2
            )
        } else {
            CornerRadius.Zero
        }

        dataList.fastForEachIndexed { groupIndex, group ->
            val groupStartX = groupIndex * groupWidth
            if (groupIndex == selectedCategory) {
                drawBar(
                    cornerRadius = cornerRadius,
                    offset = Offset(groupStartX, 0f),
                    size = Size(width = groupWidth, height = canvasHeight),
                    brush = SolidColor(Color.Gray.copy(alpha = 0.1F))
                )
            }
            group.bars.fastForEachIndexed { barIndex, barValue ->
                val barHeight = (barValue / maxValue) * canvasHeight

                val barX = groupStartX +
                        (groupWidth - (group.bars.size * (barWidth + barWidth / 4))) / 2 +
                        barIndex * (barWidth + barWidth / 4)

                val brush = Brush.linearGradient(
                    colors = group.colors[barIndex].value,
                    start = Offset(
                        x = barX,
                        y = canvasHeight - barHeight
                    ),
                    end = Offset(
                        x = barX + barWidth,
                        y = canvasHeight
                    )
                )

                drawBar(
                    brush = brush,
                    size = Size(width = barWidth, height = barHeight),
                    cornerRadius = cornerRadius,
                    offset = Offset(barX, canvasHeight - barHeight)
                )
            }

            for (barIndex in group.bars.size until maxBarsCount) {
                val totalBarWidth = barWidth + barWidth / 4
                val groupOffset = (groupWidth - (group.bars.size * totalBarWidth)) / 2
                val barX = groupStartX + groupOffset + barIndex * totalBarWidth
                drawBar(
                    size = Size(width = barWidth, height = 0F),
                    brush = SolidColor(Color.Transparent),
                    cornerRadius = cornerRadius,
                    offset = Offset(barX, canvasHeight)
                )
            }

            if (labelConfig.showXLabel) {
                val textCharCount = labelConfig.getXLabelTextCharCount(
                    xValue = group.label,
                    displayDataCount = groupCount
                )
                val textSizeFactor = groupCount * if (groupCount <= 5) 20 else 40
                val textLayoutResult = textMeasurer.measure(
                    text = group.label.take(textCharCount),
                    style = labelConfig.getTetStyle(fontSize = (canvasWidth / textSizeFactor).sp)
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
}

private fun DrawScope.drawBar(
    size: Size,
    brush: Brush,
    cornerRadius: CornerRadius,
    offset: Offset
) {
    getDrawingPath(
        individualBarTopLeft = offset,
        individualBarRectSize = size,
        cornerRadius = cornerRadius
    ).let { path ->
        drawPath(path = path, brush = brush)
    }
}
