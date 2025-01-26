package com.himanshoe.charty.bar.model

import androidx.compose.ui.graphics.Color
import com.himanshoe.charty.common.ChartData

data class BarData(
    override val yValue: Float,
    override val xValue: Any,
    val barColor: Color,
    val barBackgroundColor: Color = Color(0x40D3D3D3)
) : ChartData {
    override val chartString: String
        get() = "Bar Chart"
}