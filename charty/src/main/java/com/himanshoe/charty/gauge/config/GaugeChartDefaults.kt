/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.gauge.config

import androidx.compose.ui.graphics.Color

/**
 * Provides default configurations for the gauge chart.
 */
object GaugeChartDefaults {

    /**
     * Returns the default configuration for the gauge chart.
     *
     * @return The default [GaugeChartConfig] for the gauge chart.
     */
    fun gaugeConfigDefaults() = GaugeChartConfig(
        primaryColor = Color(0xFF20A100),
        placeHolderColor = Color(0xFFABEDCD),
        strokeWidth = 48F,
        showNeedle = true,
        showIndicator = true,
        showText = true,
        indicatorColor = Color(0xffed625d),
        textColor = Color(0xffed625d),
        indicatorWidth = 8F
    )

    /**
     * Returns the default configuration for the needle in the gauge chart.
     *
     * @return The default [NeedleConfig] for the needle in the gauge chart.
     */
    fun needleConfigDefaults() = NeedleConfig(
        color = Color(0xFF93A047),
        strokeWidth = 40f,
    )
}
