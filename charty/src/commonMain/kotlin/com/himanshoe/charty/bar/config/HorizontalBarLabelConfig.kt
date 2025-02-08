package com.himanshoe.charty.bar.config

import androidx.compose.ui.graphics.Color
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.common.asGradientChartColor

/**
 * Data class representing the configuration for horizontal bar labels.
 *
 * @property showLabel Indicates whether the label should be shown.
 * @property hasOverlappingLabel Indicates whether the label has overlapping.
 * @property textColors List of colors for the text.
 * @property textBackgroundColors List of background colors for the text.
 */
data class HorizontalBarLabelConfig(
    val showLabel: Boolean,
    val hasOverlappingLabel: Boolean,
    val textColors: ChartColor,
    val textBackgroundColors: ChartColor
) {
    companion object {
        /**
         * Provides a default configuration for horizontal bar labels.
         *
         * @return A `HorizontalBarLabelConfig` object with default settings.
         */
        fun default() = HorizontalBarLabelConfig(
            showLabel = false,
            hasOverlappingLabel = true,
            textColors = listOf(
                Color(0xffFFAFBD),
                Color(0xffffc3a0)
            ).asGradientChartColor(),
            textBackgroundColors = listOf(
                Color(0xff0f0c29),
                Color(0xff302b63),
                Color(0xff24243e)
            ).asGradientChartColor(),
        )
    }
}
