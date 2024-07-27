/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.line.model

import androidx.compose.runtime.Immutable
import com.himanshoe.charty.common.ChartData

/**
 * Represents a data point for a line chart.
 *
 * @property yValue The y-axis value of the data point.
 * @property xValue The x-axis value of the data point.
 */
@Immutable
data class LineData(override val yValue: Float, override val xValue: Any) : ChartData {
    override val chartString: String
        get() = "Line Chart"
}
