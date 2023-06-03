package com.himanshoe.charty.line.model

import androidx.compose.runtime.Immutable
import com.himanshoe.charty.common.ChartData
import com.himanshoe.charty.point.model.Point
import com.himanshoe.charty.point.model.PointData

@Immutable
data class LineData(override val yValue: Float, override val xValue: Any) : ChartData {
    override val chartString: String
        get() = "Line Chart"
}
