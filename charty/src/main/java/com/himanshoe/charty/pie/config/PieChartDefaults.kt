package com.himanshoe.charty.pie.config

import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

object PieChartDefaults {

    fun defaultConfig() = PieChartConfig(donut = true, showLabel = true)

    fun defaultTextLabelConfig() = PieChartLabelTextConfig(
        textSize = TextUnit.Unspecified,
        fontStyle = null,
        fontWeight = null,
        fontFamily = null,
        maxLine = 1,
        overflow = TextOverflow.Ellipsis
    )
}
