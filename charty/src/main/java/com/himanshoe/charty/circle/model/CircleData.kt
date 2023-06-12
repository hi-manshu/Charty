package com.himanshoe.charty.circle.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.himanshoe.charty.common.ChartData

@Immutable
data class CircleData(override val yValue: Float, override val xValue: Any, val color: Color) :
    ChartData {
    override val chartString: String
        get() = "Circle Chart"
}
