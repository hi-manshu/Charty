package com.himanshoe.charty.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

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
    val xAxisCharCount: Int?,
    val labelTextStyle: TextStyle?,
) {
    companion object {
        /**
         * Returns the default configuration for labels.
         *
         * @return A LabelConfig object with default settings.
         */
        fun default() = LabelConfig(
            textColor = Color.Black.asSolidChartColor(),
            showXLabel = false,
            xAxisCharCount = null,
            labelTextStyle = null,
            showYLabel = false,
        )
    }
}
