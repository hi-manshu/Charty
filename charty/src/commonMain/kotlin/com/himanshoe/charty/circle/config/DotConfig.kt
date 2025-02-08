package com.himanshoe.charty.circle.config

import androidx.compose.ui.graphics.Color
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.common.asSolidChartColor

/**
 * Configuration for the dots displayed along the arc in the speedometer progress bar.
 *
 * @param fillDotColor The color of the filled dots.
 * @param trackDotColor The color of the track dots.
 * @param count The number of dots to be displayed along the arc.
 * @param showDots A boolean indicating whether to show the dots or not.
 */
data class DotConfig(
    val fillDotColor: ChartColor,
    val trackDotColor: ChartColor,
    val count: Int = 50,
    val showDots: Boolean = true
) {
    companion object {
        /**
         * Returns the default configuration for the dots.
         *
         * @return A DotConfig object with default values.
         */
        fun default() = DotConfig(
            fillDotColor = ChartColor.Gradient(
                listOf(
                    Color(0xFFCB356B),
                    Color(0xFFBD3F32),
                )
            ),
            trackDotColor = Color.Gray.copy(alpha = 0.2F).asSolidChartColor()
        )
    }
}
