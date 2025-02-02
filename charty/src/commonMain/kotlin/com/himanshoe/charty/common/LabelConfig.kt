package com.himanshoe.charty.common

import androidx.compose.ui.graphics.Color

/**
 * Data class representing the configuration for labels in a chart.
 *
 * @property textColor The color of the text for the labels.
 * @property showXLabel A boolean indicating whether to show labels on the x-axis.
 * @property showYLabel A boolean indicating whether to show labels on the y-axis.
 */
data class LabelConfig(
    val textColor: ChartColor,
    val showXLabel: Boolean,
    val showYLabel: Boolean,
) {
    companion object {
        /**
         * Returns the default configuration for labels.
         *
         * @return A LabelConfig object with default settings.
         */
        fun default() = LabelConfig(
            textColor = Color.Black.asSolidChartColor(),
            showXLabel = true,
            showYLabel = true,
        )
    }
}