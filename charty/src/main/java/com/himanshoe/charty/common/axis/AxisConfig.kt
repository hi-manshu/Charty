package com.himanshoe.charty.common.axis

import androidx.compose.ui.graphics.Color

data class AxisConfig(
    val showAxes: Boolean,
    val showUnitLabels: Boolean,
    val xAxisColor: Color = Color.LightGray,
    val yAxisColor: Color = Color.LightGray,
)

internal object AxisConfigDefaults {

    fun axisConfigDefaults() = AxisConfig(
        xAxisColor = Color.LightGray,
        showAxes = true,
        showUnitLabels = true,
        yAxisColor = Color.LightGray,
    )
}
