package com.himanshoe.charty.common

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class TextConfig(
    val textSize: TextUnit,
    val textColor: ChartColor,
    val style: TextStyle
) {
    companion object {
        fun default(
            textSize: TextUnit = 12.sp,
            color: ChartColor = Color.Gray.asSolidChartColor(),
            style: TextStyle = TextStyle(brush = Brush.linearGradient(color.value))
        ) = TextConfig(
            textSize = textSize, textColor = color, style = style
        )
    }
}