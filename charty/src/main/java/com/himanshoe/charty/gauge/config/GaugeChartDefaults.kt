/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.gauge.config

import androidx.compose.ui.graphics.Color

object GaugeChartDefaults {

    fun gaugeConfigDefaults() = GaugeChartConfig(
        primaryColor = Color(0xFF20A100),
        placeHolderColor = Color(0xFFABEDCD),
        strokeWidth = 48F,
        showNeedle = true,
        showIndicator = true,
        indicatorColor = Color(0xffed625d),
        indicatorWidth = 8F
    )

    fun needleConfigDefaults() = NeedleConfig(
        color = Color(0xFF93A047),
        strokeWidth = 40f,
    )
}
