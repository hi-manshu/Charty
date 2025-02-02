package com.himanshoe.charty.common

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Draws a target line on the canvas if the target value is provided.
 *
 * @param target The target value to be drawn as a line on the canvas. If null, no line is drawn.
 * @param minValue The minimum value of the y-axis.
 * @param yScale The scale factor for the y-axis.
 * @param canvasHeight The height of the canvas.
 * @param canvasWidth The width of the canvas.
 * @param targetConfig The configuration for the target line, including color, stroke width, and path effect.
 */
internal fun DrawScope.drawTargetLineIfNeeded(
    target: Float?,
    minValue: Float,
    yScale: Float,
    canvasHeight: Float,
    canvasWidth: Float,
    targetConfig: TargetConfig
) {
    target?.let { targetValue ->
        val y = canvasHeight - (targetValue - minValue) * yScale
        drawLine(
            brush = Brush.linearGradient(targetConfig.targetLineBarColors.value),
            start = Offset(0f, y),
            end = Offset(canvasWidth, y),
            pathEffect = targetConfig.pathEffect,
            strokeWidth = targetConfig.targetStrokeWidth
        )
    }
}