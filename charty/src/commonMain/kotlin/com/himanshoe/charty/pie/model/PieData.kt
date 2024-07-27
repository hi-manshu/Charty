/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.pie.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.himanshoe.charty.common.ChartData

/**
 * Represents a data point in a pie chart.
 *
 * @property yValue The value of the data point.
 * @property xValue The label or identifier of the data point.
 * @property color The color associated with the data point.
 */
@Immutable
data class PieData(override val yValue: Float, override val xValue: Any, val color: Color) :
    ChartData {
    override val chartString: String
        get() = "Pie Chart"
}
