/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.gauge

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import com.himanshoe.charty.gauge.config.GaugeChartConfig
import com.himanshoe.charty.gauge.config.GaugeChartDefaults

@Composable
fun GaugeChart(
    percentValue: Int,
    modifier: Modifier = Modifier,
    gaugeChartConfig: GaugeChartConfig = GaugeChartDefaults.configDefaults(),
    animated: Boolean = true,
    animationSpec: AnimationSpec<Float> = tween(),
    startAngle: Float = 135f,
    endAngle: Float = 405f,
) {
    require(percentValue in 1..100) { "percentValue must be within the range of 1 to 100" }

    val animatedPercent = rememberAnimatedPercent(animated, percentValue, animationSpec)
    Box(modifier = modifier.aspectRatio(1f)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = (size.width - gaugeChartConfig.strokeWidth) / 2
            val sweepAngle = endAngle - startAngle

            // Draw background arc
            drawArc(
                color = gaugeChartConfig.placeHolderColor,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = gaugeChartConfig.strokeWidth, cap = StrokeCap.Round)
            )

            val currentSweepAngle = animatedPercent * sweepAngle
            drawArc(
                color = gaugeChartConfig.primaryColor,
                startAngle = startAngle,
                sweepAngle = currentSweepAngle,
                useCenter = false,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = gaugeChartConfig.strokeWidth, cap = StrokeCap.Round)
            )
        }
    }
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
fun GaugeChartPreview() {
    val percentValue = 100
    val primaryColor = Color.Blue
    GaugeChart(percentValue = percentValue)
}
