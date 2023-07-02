/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.circle.config

import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.himanshoe.charty.common.config.StartAngle

/**
 * Provides default configuration values for a circle chart and its labels.
 */
object CircleConfigDefaults {
    /**
     * Returns the default configuration for a circle chart.
     */
    fun circleChartConfig() = CircleChartConfig(
        startAngle = StartAngle.Zero,
        maxValue = null,
        showLabel = true
    )

    /**
     * Returns the default configuration for the labels in a circle chart.
     */
    fun defaultTextLabelConfig() = CircleChartLabelTextConfig(
        textSize = TextUnit.Unspecified,
        fontStyle = null,
        fontWeight = null,
        fontFamily = null,
        maxLine = 1,
        overflow = TextOverflow.Ellipsis
    )
}
