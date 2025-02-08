package com.himanshoe.charty.point.model

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.ui.graphics.PathEffect

/**
 * Data class representing the configuration for a point chart.
 *
 * @property axisLineWidth The width of the axis lines.
 * @property gridLineWidth The width of the grid lines.
 * @property circleRadius The radius of the circles in the chart.
 * @property showClickedBar A boolean indicating whether to show a bar when a point is clicked.
 * @property animationDurationMillis The duration of the animation in milliseconds.
 * @property animationEasing The easing function for the animation.
 * @property animatePoints A boolean indicating whether to animate the points.
 * @property gridLinePathEffect The path effect for the grid lines.
 */
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
