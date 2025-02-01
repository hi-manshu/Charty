package com.himanshoe.charty.point.model

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.ui.graphics.PathEffect

data class PointChartConfig(
    val axisLineWidth: Float = 2f,
    val gridLineWidth: Float = 1f,
    val circleRadius: Float = 10f,
    val showClickedBar: Boolean = true,
    val animationDurationMillis: Int = 500,
    val animationEasing: Easing = LinearEasing,
    val animatePoints: Boolean = true,
    val gridLinePathEffect: PathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
)