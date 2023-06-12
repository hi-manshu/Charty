package com.himanshoe.charty.bar.model

import androidx.compose.ui.graphics.Color
import com.himanshoe.charty.common.ChartData

data class BarData(override val yValue: Float, override val xValue: Any, val color: Color? = null) :
    ChartData {
    override val chartString: String
        get() = "Bar Chart"
}
