/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.circle.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.himanshoe.charty.common.ChartData

/**
 * Represents the data for a circle in a chart.
 *
 * @param yValue The y-value of the circle.
 * @param xValue The x-value of the circle.
 * @param color The color of the circle.
 */
@Immutable
data class CircleData(override val yValue: Float, override val xValue: Any, val color: Color) :
    ChartData {
    override val chartString: String
        get() = "Circle Chart"
}
