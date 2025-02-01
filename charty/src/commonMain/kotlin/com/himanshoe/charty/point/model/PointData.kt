package com.himanshoe.charty.point.model

import com.himanshoe.charty.common.ChartData

data class PointData(
    override val yValue: Float,
    override val xValue: Any,
) : ChartData {
    override val chartString: String
        get() = "Circle Chart"
}
