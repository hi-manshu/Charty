package com.himanshoe.charty.circle

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.util.fastMap
import com.himanshoe.charty.circle.model.CircleData
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * A composable function that displays a circle chart based on the provided data.
 *
 * @param data A lambda function that returns a list of CircleData objects representing the data to be displayed.
 * @param modifier A Modifier to be applied to the Canvas.
 * @param onCircleClick A lambda function to handle click events on the circle. It receives the corresponding `CircleData` as parameters.
 */
@Composable
fun CircleChart(
    data: () -> List<CircleData>,
    modifier: Modifier = Modifier,
    showEndIndicator: Boolean = true,
    onCircleClick: (CircleData) -> Unit = {}
) {
    CircleChartContent(
        modifier = modifier,
        onCircleClick = onCircleClick,
        data = data,
        showEndIndicator = showEndIndicator,
    )
}

@Composable
private fun CircleChartContent(
    data: () -> List<CircleData>,
    modifier: Modifier = Modifier,
    showEndIndicator: Boolean = true,
    onCircleClick: (CircleData) -> Unit = {}
) {
    var clickedCircleIndex by remember { mutableStateOf(-1) }
    val circleData = data()

    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val maxRadius = min(size.width, size.height) / 2
                    val center = Offset((size.width / 2).toFloat(), (size.height / 2).toFloat())
                    val radiusFactor = if (circleData.count() == 2) 1.55F else 1.45F
                    val strokeWidth = maxRadius / (radiusFactor * circleData.size)
                    val radiusStep = (maxRadius - strokeWidth) / circleData.size

                    circleData.fastForEachIndexed { index, _ ->
                        val currentRadius = maxRadius - (index * radiusStep) - strokeWidth
                        val distance = (offset - center).getDistance()
                        if (distance in (currentRadius - strokeWidth / 2)..(currentRadius + strokeWidth / 2)) {
                            clickedCircleIndex = index
                        }
                    }
                }
            }
    ) {
        val maxRadius = min(size.width, size.height) / 2
        val center = Offset(size.width / 2, size.height / 2)
        val radiusFactor = if (circleData.count() == 2) 1.55F else 1.45F
        val strokeWidth = maxRadius / (radiusFactor * circleData.size)
        val radiusStep = (maxRadius - strokeWidth) / circleData.size

        circleData.fastForEachIndexed { index, item ->
            val currentRadius = maxRadius - (index * radiusStep) - strokeWidth
            val sweepAngle = 360 * (item.value / 100)
            val scaleFactor = if (index == clickedCircleIndex) 1.006F else 1.0F
            val scaledRadius = currentRadius * scaleFactor
            val scaledStrokeWidth = strokeWidth * scaleFactor

            // Draw the background circle
            drawCircle(
                brush = Brush.linearGradient(
                    item.trackColor.value.fastMap {
                        it.copy(alpha = 0.1F)
                    }
                ),
                radius = scaledRadius,
                center = center,
                style = Stroke(width = scaledStrokeWidth)
            )

            // Draw the foreground arc
            drawArc(
                brush = Brush.linearGradient(item.color.value),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(center.x - scaledRadius, center.y - scaledRadius),
                size = Size(scaledRadius * 2, scaledRadius * 2),
                style = Stroke(width = scaledStrokeWidth, cap = StrokeCap.Round)
            )

            if (showEndIndicator) {
                // Draw the shadow at the end of the tip
                val endAngle = -90f + sweepAngle
                val endX = center.x + scaledRadius * cos(endAngle.toRadians())
                val endY = center.y + scaledRadius * sin(endAngle.toRadians())
                drawCircle(
                    center = Offset(endX, endY),
                    radius = scaledStrokeWidth / 2,
                    color = Color.Black.copy(alpha = 0.1f),
                )
            }
            if (index == clickedCircleIndex) {
                onCircleClick(item)
            }
        }
    }
}

private fun Float.toRadians(): Float = (this * PI / 180).toFloat()
