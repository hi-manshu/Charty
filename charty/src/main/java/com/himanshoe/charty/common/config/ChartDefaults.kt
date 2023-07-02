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

    /**
     * Provides default values for the axis configuration.
     */
    fun axisConfigDefaults() = AxisConfig(
        showGridLabel = true,
        showAxes = true,
        showGridLines = true,
        axisStroke = 2F,
        axisColor = Color.Black,
        minLabelCount = 2
    )

    /**
     * Provides default colors for the chart.
     */
    fun colorDefaults() = ChartColors(
        contentColor = listOf(
            Color(0xffed625d),
            Color(0xfff79f88)
        ),
        backgroundColors = listOf(
            Color.Transparent, Color.Transparent
        )
    )

    /**
     * Provides default values for text label configuration in the chart.
     */
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
