package com.himanshoe.charty.bar.config

data class StackBarConfig(
    val showAxisLines: Boolean,
    val showGridLines: Boolean,
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
            showCurvedBar = false,
            minimumBarCount = 7,
        )
    }
}
