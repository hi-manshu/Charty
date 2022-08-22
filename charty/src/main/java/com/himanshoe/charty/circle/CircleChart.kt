package com.himanshoe.charty.circle

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import com.himanshoe.charty.circle.config.CircleConfig
import com.himanshoe.charty.circle.config.CircleConfigDefaults
import com.himanshoe.charty.circle.model.CircleData
import com.himanshoe.charty.circle.model.maxYValue

@Composable
fun CircleChart(
    circleData: List<CircleData>,
    modifier: Modifier = Modifier,
    color: Color,
    isAnimated: Boolean = true,
    config: CircleConfig = CircleConfigDefaults.circleConfigDefaults()
) {
    CircleChart(
        circleData = circleData,
        colors = listOf(color, color),
        modifier = modifier,
        config = config,
        isAnimated = isAnimated,
    )
}

@Composable
fun CircleChart(
    circleData: List<CircleData>,
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(Color.Transparent, Color.Transparent),
    config: CircleConfig = CircleConfigDefaults.circleConfigDefaults(),
    isAnimated: Boolean = true,
) {
    val maxYValueState = rememberSaveable { mutableStateOf(circleData.maxYValue()) }
    val maxYValue = maxYValueState.value
    val angleFactor = if (config.maxValue != null) 360.div(config.maxValue) else 360.div(maxYValue)

    val animatedFactor = remember {
        Animatable(initialValue = 0f)
    }

    LaunchedEffect(key1 = true) {
        animatedFactor.animateTo(
            targetValue = angleFactor,
            animationSpec = tween(1000)
        )
    }

    Canvas(modifier = modifier) {
        val scaleFactor = size.width.div(circleData.count())
        val sizeArc = size.div(scaleFactor)

        circleData.forEachIndexed { index, circleData ->
            val circleColor: List<Color> =
                if (circleData.color != null) listOf(circleData.color, circleData.color) else colors
            val arcWidth = sizeArc.width.plus(index.times(scaleFactor))
            val arcHeight = sizeArc.height.plus(index.times(scaleFactor))
            val factor = if (isAnimated) animatedFactor.value else angleFactor

            drawArc(
                brush = Brush.linearGradient(circleColor),
                startAngle = config.startPosition.angle,
                sweepAngle = factor.times(circleData.yValue),
                topLeft = Offset(
                    (size.width - arcWidth).div(2f),
                    (size.height - arcHeight).div(2f)
                ),
                useCenter = false,
                style = Stroke(width = scaleFactor.div(2.5F), cap = StrokeCap.Round),
                size = Size(arcWidth, arcHeight)
            )
        }
    }
}
