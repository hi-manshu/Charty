/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.gauge

import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import com.himanshoe.charty.gauge.config.GaugeChartConfig
import com.himanshoe.charty.gauge.config.GaugeChartDefaults
import com.himanshoe.charty.gauge.config.NeedleConfig
import kotlin.math.cos
import kotlin.math.sin

private const val START_ANGLE = 135F
private const val INITIAL_START_ANGLE = 405F

/**
 * A composable function that displays a gauge chart.
 *
 * @param percentValue The value in percentage to be displayed on the gauge chart.
 * @param modifier The modifier for styling or positioning the gauge chart.
 * @param gaugeChartConfig The configuration for the gauge chart appearance.
 * @param needleConfig The configuration for the needle in the gauge chart.
 * @param animated Determines whether the gauge chart should be animated.
 * @param animationSpec The animation specification for the gauge chart animation.
 */
@Composable
fun GaugeChart(
    percentValue: Int,
    modifier: Modifier = Modifier,
    gaugeChartConfig: GaugeChartConfig = GaugeChartDefaults.gaugeConfigDefaults(),
    needleConfig: NeedleConfig = GaugeChartDefaults.needleConfigDefaults(),
    animated: Boolean = true,
    animationSpec: AnimationSpec<Float> = tween(),
) {
    require(percentValue in 0..100) { "percentValue must be within the range of 1 to 100" }

    val animatedPercent = rememberAnimatedPercent(animated, percentValue, animationSpec)
    Box(modifier = modifier.aspectRatio(1f)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = (size.width - gaugeChartConfig.strokeWidth) / 2
            val sweepAngle = INITIAL_START_ANGLE - START_ANGLE

            // Draw background arc
            drawArc(
                color = gaugeChartConfig.placeHolderColor,
                startAngle = START_ANGLE,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = gaugeChartConfig.strokeWidth, cap = StrokeCap.Round)
            )
            if (gaugeChartConfig.showIndicator) {
                // Draw minute hour dividers
                val dividerCount = 100
                val dividerSweepAngle = sweepAngle / dividerCount
                val longerDividerLength = radius * 0.1f
                val shorterDividerLength = radius * 0.05f

                for (i in 0..dividerCount) { // Updated loop range to include 0
                    val dividerStartAngle = START_ANGLE + (i * dividerSweepAngle)
                    val isLongerDivider = i % 10 == 0

                    val dividerLength =
                        if (isLongerDivider) longerDividerLength else shorterDividerLength
                    val startPoint = polarToCartesian(
                        centerX,
                        centerY,
                        radius - gaugeChartConfig.strokeWidth,
                        dividerStartAngle
                    )
                    val endPoint = polarToCartesian(
                        centerX,
                        centerY,
                        radius - gaugeChartConfig.strokeWidth - dividerLength,
                        dividerStartAngle
                    )
                    val color =
                        if (isLongerDivider) gaugeChartConfig.indicatorColor else gaugeChartConfig.indicatorColor.copy(
                            alpha = 0.8F
                        )
                    drawLine(
                        color = color,
                        start = Offset(startPoint.x, startPoint.y),
                        end = Offset(endPoint.x, endPoint.y),
                        strokeWidth = gaugeChartConfig.indicatorWidth,
                        cap = StrokeCap.Round
                    )
                }
            }
            val currentSweepAngle = animatedPercent * sweepAngle
            drawArc(
                color = gaugeChartConfig.primaryColor,
                startAngle = START_ANGLE,
                sweepAngle = currentSweepAngle,
                useCenter = false,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = gaugeChartConfig.strokeWidth, cap = StrokeCap.Round)
            )

            if (gaugeChartConfig.showNeedle) {
                // Calculate needle path
                val needleAngle = START_ANGLE + currentSweepAngle
                val needlePath = Path().apply {
                    val startPoint =
                        polarToCartesian(centerX, centerY, radius.div(1.2F), needleAngle)
                    val endPoint = polarToCartesian(centerX, centerY, radius.div(1.2F), needleAngle)

                    moveTo(centerX, centerY)
                    lineTo(startPoint.x, startPoint.y)
                    lineTo(endPoint.x, endPoint.y)
                }

                // Draw needle
                drawPath(
                    path = needlePath,
                    color = needleConfig.color,
                    style = Stroke(width = needleConfig.strokeWidth, cap = StrokeCap.Round)
                )
            }
            if (gaugeChartConfig.showText) {
                drawContext.canvas.nativeCanvas.drawText(
                    "$percentValue %",
                    center.x,
                    size.height - size.height / 4,
                    Paint().apply {
                        color = gaugeChartConfig.textColor.toArgb()
                        textSize = size.width / 25
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
        }
    }
}

private fun polarToCartesian(
    centerX: Float,
    centerY: Float,
    radius: Float,
    angle: Float
): Offset {
    val x = centerX + radius * cos(Math.toRadians(angle.toDouble())).toFloat()
    val y = centerY + radius * sin(Math.toRadians(angle.toDouble())).toFloat()
    return Offset(x, y)
}

@Composable
private fun rememberAnimatedPercent(
    animated: Boolean,
    percentValue: Int,
    animationSpec: AnimationSpec<Float>
): Float {
    val animatedPercent = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(Unit) {
        if (animated) {
            animatedPercent.animateTo(
                targetValue = percentValue / 100f,
                animationSpec = animationSpec
            )
        } else {
            animatedPercent.snapTo(percentValue / 100f)
        }
    }

    return animatedPercent.value
}

@Preview
@Composable
private fun GaugeChartPreview() {
    val percentValue = 100
    GaugeChart(percentValue = percentValue)
}
