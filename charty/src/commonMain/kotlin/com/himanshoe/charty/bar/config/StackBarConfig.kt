package com.himanshoe.charty.bar.config

data class StackBarConfig(
    val showAxisLines: Boolean,
    val showGridLines: Boolean,
    val drawNegativeValueChart: Boolean,
    val showCurvedBar: Boolean,
    val minimumBarCount: Int,
) {
    companion object {
        /**
         * Provides a default configuration for bar chart settings.
         *
         * @return A `StackBarConfig` object with default settings.
         */
        fun default() = StackBarConfig(
            showAxisLines = true,
            showGridLines = true,
            drawNegativeValueChart = true,
            showCurvedBar = true,
            minimumBarCount = 7,
        )
    }
}
