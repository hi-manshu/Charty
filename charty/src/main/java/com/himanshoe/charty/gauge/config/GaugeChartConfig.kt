/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.gauge.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Represents the configuration for a gauge chart.
 *
 * @param placeHolderColor The color of the placeholder arc in the gauge chart.
 * @param primaryColor The color of the primary arc in the gauge chart.
 * @param textColor The color of the text in the gauge chart.
 * @param showText Indicates whether to show the text in the gauge chart.
 * @param strokeWidth The width of the arcs in the gauge chart.
 * @param showNeedle Indicates whether to show the needle in the gauge chart.
 * @param showIndicator Indicates whether to show the indicator in the gauge chart.
 * @param indicatorColor The color of the indicator in the gauge chart.
 * @param indicatorWidth The width of the indicator in the gauge chart.
 */
@Immutable
data class GaugeChartConfig(
    val placeHolderColor: Color,
    val primaryColor: Color,
    val strokeWidth: Float,
    val showNeedle: Boolean,
    val showIndicator: Boolean,
    val indicatorColor: Color,
    val textColor: Color,
    val showText: Boolean,
    val indicatorWidth: Float,
)
