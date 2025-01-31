package com.himanshoe.charty.common

import androidx.compose.ui.graphics.Color

data class LabelConfig(
    val textColor: Color,
    val showXLabel: Boolean,
    val showYLabel: Boolean,
) {
    companion object {
        fun default() = LabelConfig(
            textColor = Color.Black,
            showXLabel = true,
            showYLabel = true,
        )
    }
}
