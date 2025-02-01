package com.himanshoe.charty.point

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.himanshoe.charty.bar.config.TargetConfig
import com.himanshoe.charty.common.LabelConfig
import com.himanshoe.charty.common.drawTargetLineIfNeeded
import com.himanshoe.charty.point.model.PointChartColorConfig
import com.himanshoe.charty.point.model.PointChartConfig
import com.himanshoe.charty.point.model.PointData
import com.himanshoe.charty.point.modifier.drawAxesAndGridLines

@Composable
fun PointChart(
    data: () -> List<PointData>,
    modifier: Modifier = Modifier,
    labelConfig: LabelConfig = LabelConfig.default(),
    colorConfig: PointChartColorConfig = PointChartColorConfig.default(),
    chartConfig: PointChartConfig = PointChartConfig(),
    target: Float? = null,
    targetConfig: TargetConfig = TargetConfig.default(),
    onPointClick: (Int, PointData) -> Unit = { _, _ -> }
) {
    PointChartContent(
        data = data,
        modifier = modifier,
        labelConfig = labelConfig,
        colorConfig = colorConfig,
        chartConfig = chartConfig,
        target = target,
        targetConfig = targetConfig,
        onPointClick = onPointClick
    )
}

@Composable
private fun PointChartContent(
    data: () -> List<PointData>,
    modifier: Modifier = Modifier,
    target: Float? = null,
    targetConfig: TargetConfig = TargetConfig.default(),
    labelConfig: LabelConfig = LabelConfig.default(),
    colorConfig: PointChartColorConfig = PointChartColorConfig.default(),
    chartConfig: PointChartConfig = PointChartConfig(),
    onPointClick: (Int, PointData) -> Unit = { _, _ -> }
) {
    val circleRadius = remember { Animatable(0F) }
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

    LaunchedEffect(circleData) {
        if (chartConfig.animatePoints) {
            circleRadius.animateTo(
                targetValue = chartConfig.circleRadius,
                animationSpec = tween(
                    durationMillis = chartConfig.animationDurationMillis,
                    easing = chartConfig.animationEasing
                )
            )
        } else {
            circleRadius.snapTo(chartConfig.circleRadius)
        }
    }

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

        drawTargetLineIfNeeded(
            target = target,
            targetConfig = targetConfig,
            minValue = minValue,
            yScale = yScale,
            canvasHeight = canvasHeight,
            canvasWidth = canvasWidth,
        )
        drawClickedBarIfNeeded(
            chartConfig = chartConfig,
            clickedIndex = clickedIndex,
            xStep = xStep,
            canvasHeight = canvasHeight,
            colorConfig = colorConfig
        )

        drawPoints(
            circleData = circleData,
            xStep = xStep,
            yScale = yScale,
            minValue = minValue,
            canvasHeight = canvasHeight,
            tapOffset = tapOffset,
            clickedIndex = clickedIndex,
            onPointClick = { index, data ->
                clickedIndex = index
                onPointClick(index, data)
            },
            circleRadius = circleRadius,
            colorConfig = colorConfig,
        )
    }
}

private fun DrawScope.drawClickedBarIfNeeded(
    chartConfig: PointChartConfig,
    clickedIndex: Int,
    xStep: Float,
    canvasHeight: Float,
    colorConfig: PointChartColorConfig
) {
    if (chartConfig.showClickedBar && clickedIndex != -1) {
        val barX = (clickedIndex + 0.5f) * xStep - xStep / 2
        drawRoundRect(
            brush = Brush.linearGradient(colorConfig.selectionBarColor.value),
            topLeft = Offset(barX, 0f),
            size = Size(width = xStep, height = canvasHeight),
            cornerRadius = CornerRadius(15F)
        )
    }
}

private fun DrawScope.drawPoints(
    circleData: List<PointData>,
    xStep: Float,
    yScale: Float,
    minValue: Float,
    canvasHeight: Float,
    tapOffset: Offset?,
    clickedIndex: Int,
    onPointClick: (Int, PointData) -> Unit,
    circleRadius: Animatable<Float, AnimationVector1D>,
    colorConfig: PointChartColorConfig,
) {
    circleData.fastForEachIndexed { index, data ->
        val x = (index + 0.5f) * xStep
        val y = canvasHeight - (data.yValue - minValue) * yScale
        tapOffset?.let { offset ->
            val barStartX = x - xStep / 2
            val barEndX = x + xStep / 2
            if (offset.x in barStartX..barEndX) {
                onPointClick(index, data)
            }
        }

        val animatedRadius =
            if (index == clickedIndex) circleRadius.value * 1.5f else circleRadius.value

        drawCircle(
            brush = Brush.linearGradient(colorConfig.circleColor.value),
            radius = animatedRadius,
            center = Offset(x, y)
        )
        drawCircle(
            brush = Brush.linearGradient(colorConfig.strokeColor.value),
            radius = animatedRadius * 1.5f,
            center = Offset(x, y)
        )
    }
}