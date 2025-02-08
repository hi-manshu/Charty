package com.himanshoe.charty.circle

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himanshoe.charty.circle.config.DotConfig
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.common.TextConfig
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * A composable function that displays a speedometer-style progress bar with an animated progress indicator.
 *
 * @param progress A lambda function that returns the current progress as a float value between 0 and 1.
 * @param title The title text to be displayed at the center of the speedometer.
 * @param color The color of the progress arc.
 * @param progressIndicatorColor The color of the progress indicator circle.
 * @param trackColor The color of the track arc.
 * @param modifier The modifier to be applied to the Box containing the speedometer.
 * @param dotConfig Configuration for the dots displayed along the arc.
 * @param titleTextConfig Configuration for the title text style.
 * @param subTitleTextConfig Configuration for the subtitle text style.
 */
@Composable
fun SpeedometerProgressBar(
    progress: () -> Float,
    title: String,
    color: ChartColor,
    progressIndicatorColor: ChartColor,
    trackColor: ChartColor,
    modifier: Modifier = Modifier,
    dotConfig: DotConfig = DotConfig.default(),
    titleTextConfig: TextConfig = TextConfig.default(),
    subTitleTextConfig: TextConfig = TextConfig.default(fontSize = 20.sp),
) {
    val progressAnimator = remember { Animatable(0f) }
    LaunchedEffect(progress()) {
        delay(400)
        progressAnimator.animateTo(progress())
    }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val size = size.minDimension
            val strokeWidth = size / 12
            val radius = size / 2 - strokeWidth / 2
            val center = Offset(size / 2, size / 2)
            val startAngle = 135f
            val sweepAngle = 270f * progressAnimator.value

            drawArc(
                brush = Brush.linearGradient(trackColor.value),
                startAngle = startAngle,
                sweepAngle = 270F,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            drawArc(
                brush = Brush.linearGradient(color.value),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            if (dotConfig.showDots) {
                drawDots(
                    dotConfig = dotConfig,
                    radius = radius,
                    strokeWidth = strokeWidth,
                    center = center,
                    startAngle = startAngle,
                    progress = { progressAnimator.value }
                )
            }

            drawProgressIndicator(
                progressIndicatorColor = progressIndicatorColor,
                radius = radius,
                center = center,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                strokeWidth = strokeWidth
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                style = titleTextConfig.style.copy(
                    fontSize = titleTextConfig.fontSize,
                    brush = Brush.linearGradient(
                        colors = titleTextConfig.textColor.value
                    )
                )
            )
            Text(
                style = subTitleTextConfig.style.copy(
                    fontSize = subTitleTextConfig.fontSize,
                    brush = Brush.linearGradient(
                        colors = subTitleTextConfig.textColor.value
                    )
                ),
                modifier = Modifier.padding(top = 8.dp),
                text = "${progressAnimator.value.times(100).toInt()}%",
            )
        }
    }
}

private fun DrawScope.drawDots(
    dotConfig: DotConfig,
    radius: Float,
    strokeWidth: Float,
    center: Offset,
    startAngle: Float,
    progress: () -> Float
) {
    val dotRadius = strokeWidth / 20
    val dotCount = dotConfig.count
    val dotSpacing = 270f / (dotCount - 1)
    val dotOffset = radius - strokeWidth / 1.2F
    for (i in 0 until dotCount) {
        val angle = (startAngle + i * dotSpacing) * PI / 180
        val dotX = center.x + dotOffset * cos(angle).toFloat()
        val dotY = center.y + dotOffset * sin(angle).toFloat()
        val currentDotColor =
            if (i <= (dotCount * progress()).toInt()) dotConfig.fillDotColor.value else dotConfig.trackDotColor.value
        drawCircle(
            brush = Brush.linearGradient(currentDotColor),
            radius = dotRadius,
            center = Offset(dotX, dotY)
        )
    }
}

private fun DrawScope.drawProgressIndicator(
    progressIndicatorColor: ChartColor,
    radius: Float,
    center: Offset,
    startAngle: Float,
    sweepAngle: Float,
    strokeWidth: Float
) {
    val progressAngle = (startAngle + sweepAngle) * PI / 180
    val progressX = center.x + radius * cos(progressAngle).toFloat()
    val progressY = center.y + radius * sin(progressAngle).toFloat()
    drawCircle(
        color = progressIndicatorColor.value.first(),
        radius = strokeWidth / 5,
        center = Offset(progressX, progressY)
    )
}
