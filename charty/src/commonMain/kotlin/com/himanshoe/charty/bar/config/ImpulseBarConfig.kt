package com.himanshoe.charty.bar.config

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

data class ImpulseBarConfig(
    val cornerRadius: CornerRadius,
    val textStyle: TextStyle,
    val blockGapRatio: Float,
    val impulseBlockHeightMultiplier: Float,
    val canAnimate: Boolean = true,
    val showTextLabel: Boolean = true
) {
    companion object {
        fun default() = ImpulseBarConfig(
            cornerRadius = CornerRadius(5F),
            textStyle = TextStyle(
                fontSize = 12.sp,
                color = Color.Black,
                fontFamily = FontFamily.Default
            ),
            blockGapRatio = 0.2F,
            impulseBlockHeightMultiplier = 1.2F
        )
    }
}
