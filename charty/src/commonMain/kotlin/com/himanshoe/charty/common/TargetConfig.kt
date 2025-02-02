package com.himanshoe.charty.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect

/**
 * Data class representing the configuration for the target line in a bar chart.
 *
 * @property targetLineBarColors A list of colors used for the gradient of the target line.
 * @property targetStrokeWidth The width of the target line.
 * @property pathEffect The path effect applied to the target line, such as dashes.
 */
data class TargetConfig(
    val targetLineBarColors: ChartColor,
    val targetStrokeWidth: Float,
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
                targetLineBarColors = ChartColor.Gradient(
                    listOf(
                        Color(0xFFffafbd),
                        Color(0xFFffc3a0),
                    )
                ),
                targetStrokeWidth = 3F,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
    }
}
