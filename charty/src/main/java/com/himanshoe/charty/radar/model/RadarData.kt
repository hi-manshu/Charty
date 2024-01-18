package com.himanshoe.charty.radar.model

import androidx.compose.runtime.Immutable
import com.himanshoe.charty.common.ChartData

/**
 * Represents a data point for a radar chart.
 *
 * @property yValue The value of the data point.
 * @property xValue The label or identifier of the data point.
 */
@Immutable
data class RadarData(
    override val yValue: Float,
    override val xValue: Any,
): ChartData {
    override val chartString: String
        get() = "Radar Chart"
}
