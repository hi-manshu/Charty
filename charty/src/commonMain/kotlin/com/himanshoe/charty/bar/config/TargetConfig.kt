package com.himanshoe.charty.bar.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect

/**
 * Data class representing the configuration for the target line in a bar chart.
 *
 * @property targetLineGradientBarColors A list of colors used for the gradient of the target line.
 * @property targetWidth The width of the target line.
 * @property pathEffect The path effect applied to the target line, such as dashes.
 */
data class TargetConfig(
    val targetLineGradientBarColors: List<Color> = emptyList(),
    val targetWidth: Float,
    val pathEffect: PathEffect?
) {
    companion object {
        /**
         * Provides a default configuration for the target line.
         *
         * @return A `TargetConfig` object with default values.
         */
        fun default() =
            TargetConfig(
                targetLineGradientBarColors = listOf(
                    Color(0xFFffafbd),
                    Color(0xFFffc3a0),
                ),
                targetWidth = 3F,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
    }
}