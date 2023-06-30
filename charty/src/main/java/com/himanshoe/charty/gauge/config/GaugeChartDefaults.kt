/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.gauge.config

import androidx.compose.ui.graphics.Color

object GaugeChartDefaults{

    fun configDefaults() = GaugeChartConfig(
        primaryColor = Color(0xFF20A100),
        placeHolderColor = Color(0xFFABEDCD),
        strokeWidth = 48F
    )
}