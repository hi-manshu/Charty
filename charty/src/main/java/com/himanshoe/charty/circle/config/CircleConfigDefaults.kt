package com.himanshoe.charty.circle.config

import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.himanshoe.charty.common.config.StartAngle

object CircleConfigDefaults {

    fun circleConfigDefaults() = CircleChartConfig(
        startAngle = StartAngle.Zero,
        maxValue = null,
        showLabel = true
    )

    fun defaultTextLabelConfig() = CircleChartLabelTextConfig(
        textSize = TextUnit.Unspecified,
        fontStyle = null,
        fontWeight = null,
        fontFamily = null,
        maxLine = 1,
        overflow = TextOverflow.Ellipsis
    )
}
