package com.himanshoe.charty.point

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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.point.model.PointChartColorConfig
import com.himanshoe.charty.point.model.PointChartConfig
import com.himanshoe.charty.point.model.PointData
import com.himanshoe.charty.point.modifier.drawAxesAndGridLines
import com.himanshoe.charty.common.LabelConfig

@Composable
fun PointChart(
    data: () -> List<PointData>,
    modifier: Modifier = Modifier,
    labelConfig: LabelConfig = LabelConfig.default(),
    colorConfig: PointChartColorConfig = PointChartColorConfig.default(),
    chartConfig: PointChartConfig = PointChartConfig(),
    onPointClick: (Int, PointData) -> Unit = { _, _ -> }
) {
    PointChartContent(
        data = data,
        modifier = modifier,
        labelConfig = labelConfig,
        colorConfig = colorConfig,
        chartConfig = chartConfig,
        onPointClick = onPointClick
    )
}

@Composable
private fun PointChartContent(
    data: () -> List<PointData>,
    modifier: Modifier = Modifier,
    labelConfig: LabelConfig = LabelConfig.default(),
    colorConfig: PointChartColorConfig = PointChartColorConfig.default(),
    chartConfig: PointChartConfig = PointChartConfig(),
    onPointClick: (Int, PointData) -> Unit = { _, _ -> }
) {
    val circleData = remember(data) { data() }
    val textMeasurer = rememberTextMeasurer()
    val (minValue, maxValue) = remember(circleData) {
        val min = circleData.minOfOrNull { it.yValue }?.takeIf { it < 0 } ?: 0f
        val max = circleData.maxOfOrNull { it.yValue } ?: 0f
        min to max
    }
    val yRange = remember(minValue, maxValue) { maxValue - minValue }
    val bottomPadding = if (labelConfig.showXLabel) 24.dp else 0.dp
    val leftPadding = if (labelConfig.showYLabel) 24.dp else 0.dp
    var clickedIndex by remember { mutableStateOf(-1) }
    var tapOffset by remember { mutableStateOf<Offset?>(null) }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = bottomPadding, start = leftPadding)
            .drawAxesAndGridLines(
                data = circleData,
                chartConfig = chartConfig,
                textMeasurer = textMeasurer,
                labelConfig = labelConfig,
                minValue = minValue,
                colorConfig = colorConfig,
                yRange = yRange
            )
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    tapOffset = offset
                }
            }
    ) {
        val (canvasWidth, canvasHeight) = size
        val xStep = canvasWidth / circleData.size
        val yScale = canvasHeight / yRange

        if (chartConfig.showClickedBar && clickedIndex != -1) {
            val barX = (clickedIndex + 0.5f) * xStep - xStep / 2
            drawRoundRect(
                brush = Brush.linearGradient(colorConfig.selectionBarColor.value),
                topLeft = Offset(barX, 0f),
                size = Size(width = xStep, height = canvasHeight),
                cornerRadius = CornerRadius(15F)
            )
        }

        circleData.fastForEachIndexed { index, data ->
            val x = (index + 0.5f) * xStep
            val y = canvasHeight - (data.yValue - minValue) * yScale
            tapOffset?.let { offset ->
                val barStartX = x - xStep / 2
                val barEndX = x + xStep / 2
                if (offset.x in barStartX..barEndX) {
                    clickedIndex = index
                    onPointClick(index, data)
                }
            }

            val circleRadius =
                if (index == clickedIndex) chartConfig.circleRadius * 1.5f else chartConfig.circleRadius

            drawCircle(
                brush = Brush.linearGradient(colorConfig.circleColor.value),
                radius = circleRadius,
                center = Offset(x, y)
            )
            drawCircle(
                brush = Brush.linearGradient(colorConfig.strokeColor.value),
                radius = circleRadius * 1.5f,
                center = Offset(x, y)
            )
        }
    }
}