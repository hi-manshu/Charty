package com.himanshoe.charty.radar.model

import androidx.compose.runtime.Immutable
import com.himanshoe.charty.common.ChartData

@Immutable
data class RadarData(
    override val yValue: Float,
    override val xValue: Any,
): ChartData {
    override val chartString: String
        get() = "Radar Chart"
}
