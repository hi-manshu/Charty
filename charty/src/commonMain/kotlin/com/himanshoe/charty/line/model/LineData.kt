package com.himanshoe.charty.line.model

import com.himanshoe.charty.common.ChartData

data class LineData(
    override val yValue: Float,
    override val xValue: Any,
) : ChartData {
    override val chartString: String
        get() = "Line Chart"
}
