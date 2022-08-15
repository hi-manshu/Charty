package com.himanshoe.charty.horizontalbar.axis

import androidx.compose.ui.graphics.Color

data class HorizontalAxisConfig(
    val showAxes: Boolean,
    val showUnitLabels: Boolean,
    val xAxisColor: Color = Color.LightGray,
    val yAxisColor: Color = Color.LightGray,
)

internal object HorizontalAxisConfigDefaults {

    fun axisConfigDefaults() = HorizontalAxisConfig(
        xAxisColor = Color.LightGray,
        showAxes = true,
        showUnitLabels = true,
        yAxisColor = Color.LightGray,
    )
}
