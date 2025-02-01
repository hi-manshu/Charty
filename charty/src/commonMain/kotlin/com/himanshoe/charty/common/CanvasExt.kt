package com.himanshoe.charty.common

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.himanshoe.charty.bar.config.TargetConfig

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