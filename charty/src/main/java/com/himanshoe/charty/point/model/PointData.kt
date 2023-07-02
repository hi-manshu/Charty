/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.point.model

import androidx.compose.runtime.Immutable
import com.himanshoe.charty.common.ChartData

/**
 * Represents data for a point in a chart.
 *
 * @param yValue The y-value of the point.
 * @param xValue The x-value of the point.
 */
@Immutable
data class PointData(override val yValue: Float, override val xValue: Any) : ChartData {
    /**
     * Returns the string representation of the chart type.
     */
    override val chartString: String
        get() = "Point Chart"
}
