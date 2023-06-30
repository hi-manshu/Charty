/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.common.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

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
        contentColor = listOf(
            Color(0xffed625d),
            Color(0xfff79f88)
        ),
        backgroundColors = listOf(
            Color.Transparent, Color.Transparent
        )
    )

    fun stackedBarColors(): List<Color> {
        return listOf(
            Color(0xFF1E88E5), // Blue
            Color(0xFF43A047), // Green
            Color(0xFFFB8C00), // Orange
            Color(0xFFE53935), // Red
        )
    }

    fun defaultTextLabelConfig() = ChartyLabelTextConfig(
        textSize = TextUnit.Unspecified,
        fontStyle = null,
        fontWeight = null,
        fontFamily = null,
        textColor = Color.Black,
        maxLine = 1,
        overflow = TextOverflow.Ellipsis
    )
}
