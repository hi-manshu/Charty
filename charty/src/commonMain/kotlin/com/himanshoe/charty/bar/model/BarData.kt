/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.bar.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.himanshoe.charty.common.ChartData

/**
 * Data class representing a bar in a bar chart.
 *
 * @param yValue The value of the bar on the y-axis.
 * @param xValue The value of the bar on the x-axis. It can be of any type (e.g., String, Int, Float, etc.).
 * @param color The color of the bar. If not specified, the default color will be used.
 */
@Immutable
data class BarData(override val yValue: Float, override val xValue: Any, val color: Color? = null) :
    ChartData {
    override val chartString: String
        get() = "Bar Chart"
}
