package com.himanshoe.charty.common

import androidx.compose.ui.graphics.Color

data class LabelConfig(
    val textColor: Color,
    val showLabel: Boolean,
) {
    companion object {
        fun default() =
            LabelConfig(
                textColor = Color.Black,
                showLabel = true,
            )
    }
}
