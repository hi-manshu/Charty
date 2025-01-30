package com.himanshoe.charty.bar.config

import androidx.compose.ui.graphics.Color


sealed class HorizontalBarLabelConfig(
    open val showLabel: Boolean,
    open val hasOverlappingLabel: Boolean,
    open val textColors: List<Color>,
    open val textBackgroundColors: List<Color>,
) {
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
            fun default() = SingleColorConfig(
                textColor = Color.White,
                backgroundColor = Color.Black,
                showLabel = false,
                hasOverlappingLabel = true,
            )
        }
    }

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