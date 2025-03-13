package com.himanshoe.charty.common

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit

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

internal fun LabelConfig.getXLabelTextCharCount(
    xValue: Any,
    displayDataCount: Int
) = xAxisCharCount ?: if (xValue.toString().length >= 3) {
    if (displayDataCount <= 7) 3 else 1
} else {
    1
}

internal fun LabelConfig.getTetStyle(
    fontSize: TextUnit,
) = labelTextStyle ?: TextStyle(
    fontSize = fontSize,
    brush = Brush.linearGradient(textColor.value)
)
