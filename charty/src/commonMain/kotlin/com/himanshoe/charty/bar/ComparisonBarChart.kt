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


    Canvas(modifier = modifier
        .padding(bottom = bottomPadding, start = leftPadding)
        .fillMaxSize()
        .drawAxesAndGridLines(
            maxValue = data().flatMap { it.bars }.maxOf { it },
            step = data().flatMap { it.bars }.maxOf { it } / 4,
            textMeasurer = textMeasurer,
            labelConfig = labelConfig,
            showAxisLines = comparisonBarChartConfig.showAxisLines,
            showGridLines = comparisonBarChartConfig.showGridLines
        )
        .pointerInput(Unit) {
            detectTapGestures { offset ->
                val groupWidth = size.width / data().size
                val clickedGroupIndex = (offset.x / groupWidth).toInt()
                if (clickedGroupIndex in data().indices) {
                    selectedCategory = clickedGroupIndex
                    onBarClick(clickedGroupIndex)
                }
            }
        }
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val maxValue = data().flatMap { it.bars }.maxOf { it }
        val groupWidth = canvasWidth / data().size
        val barWidth = groupWidth / (data().maxOf { it.bars.size } * 2)
        val cornerRadius = if (comparisonBarChartConfig.showCurvedBar) CornerRadius(
            barWidth / 2,
            barWidth / 2
        ) else CornerRadius.Zero

        data().fastForEachIndexed { groupIndex, group ->
            val groupStartX = groupIndex * groupWidth
            val maxBars = data().maxOf { it.bars.size }
            if (groupIndex == selectedCategory) {
                // Draw a bar filling the max height of the canvas
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

            // Draw missing bars as zero height
            for (barIndex in group.bars.size until maxBars) {
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

            val textCharCount = if (data().count() <= 7) 3 else 1
            val textSizeFactor = data().count() * if (data().count() <= 5) 20 else 40
            // Draw label
            val textLayoutResult = textMeasurer.measure(
                text = group.label.take(textCharCount),
                style = TextStyle(fontSize = (canvasWidth / textSizeFactor).sp)
            )
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(
                    x = groupStartX + groupWidth / 2 - textLayoutResult.size.width / 2,
                    y = canvasHeight + (canvasHeight * 0.01f) // 1% padding from bottom
                ),
                brush = SolidColor(Color.Black)
            )
        }
    }
}


private fun DrawScope.drawBar(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    brush: Brush,
    cornerRadius: CornerRadius
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