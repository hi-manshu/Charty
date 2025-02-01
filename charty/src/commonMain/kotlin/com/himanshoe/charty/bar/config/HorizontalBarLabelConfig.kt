package com.himanshoe.charty.bar.config

import androidx.compose.ui.graphics.Color

/**
 * Sealed class representing the configuration for horizontal bar labels.
 *
 * @property showLabel Indicates whether the label should be shown.
 * @property hasOverlappingLabel Indicates whether the label has overlapping.
 * @property textColors List of colors for the text.
 * @property textBackgroundColors List of background colors for the text.
 */
sealed class HorizontalBarLabelConfig(
    open val showLabel: Boolean,
    open val hasOverlappingLabel: Boolean,
    open val textColors: List<Color>,
    open val textBackgroundColors: List<Color>,
) {
    /**
     * Data class representing a single color configuration for horizontal bar labels.
     *
     * @property textColor The color of the text.
     * @property showLabel Indicates whether the label should be shown.
     * @property hasOverlappingLabel Indicates whether the label has overlapping.
     * @property backgroundColor The background color of the text.
     */
    data class SingleColorConfig(
        val textColor: Color,
        override val showLabel: Boolean,
        override val hasOverlappingLabel: Boolean,
        val backgroundColor: Color,
    ) : HorizontalBarLabelConfig(
        showLabel = showLabel,
        hasOverlappingLabel = hasOverlappingLabel,
        textColors = listOf(textColor, textColor),
        textBackgroundColors = listOf(backgroundColor, backgroundColor)
    ) {
        companion object {
            /**
             * Returns the default single color configuration.
             *
             * @return The default SingleColorConfig instance.
             */
            fun default() = SingleColorConfig(
                textColor = Color.White,
                backgroundColor = Color.Black,
                showLabel = false,
                hasOverlappingLabel = true,
            )
        }
    }

    /**
     * Data class representing a multi-color configuration for horizontal bar labels.
     *
     * @property textColors List of colors for the text.
     * @property showLabel Indicates whether the label should be shown.
     * @property hasOverlappingLabel Indicates whether the label has overlapping.
     * @property textBackgroundColors List of background colors for the text.
     */
    data class MultiColorConfig(
        override val textColors: List<Color>,
        override val showLabel: Boolean,
        override val hasOverlappingLabel: Boolean,
        override val textBackgroundColors: List<Color>,
    ) : HorizontalBarLabelConfig(
        showLabel = showLabel,
        hasOverlappingLabel = hasOverlappingLabel,
        textColors = textColors,
        textBackgroundColors = textBackgroundColors
    ) {
        companion object {
            /**
             * Returns the default multi-color configuration.
             *
             * @return The default MultiColorConfig instance.
             */
            fun default() = MultiColorConfig(
                textColors = listOf(Color(0xffFFAFBD), Color(0xffffc3a0)),
                textBackgroundColors = listOf(
                    Color(0xff0f0c29),
                    Color(0xff302b63),
                    Color(0xff24243e)
                ),
                showLabel = false,
                hasOverlappingLabel = true,
            )
        }
    }
}