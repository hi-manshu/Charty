package com.himanshoe.charty.point.model

import androidx.compose.runtime.Immutable
import com.himanshoe.charty.common.ChartData

@Immutable
data class PointData(override val yValue: Float, override val xValue: Any) : ChartData {
    override val chartString: String
        get() = "Point Chart"
}
