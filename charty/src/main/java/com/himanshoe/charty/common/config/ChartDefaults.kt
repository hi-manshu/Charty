package com.himanshoe.charty.common.config

import androidx.compose.ui.graphics.Color

object ChartDefaults {

    fun axisConfigDefaults() = AxisConfig(
        showGridLabel = true,
        showAxes = true,
        showGridLines = true,
        axisStroke = 2F,
        axisColor = Color.Black,
        minLabelCount = 2
    )

    fun colorDefaults() = ChartColors(
        listOf(
            Color(0xffed625d),
            Color(0xfff79f88)
        )
    )
}