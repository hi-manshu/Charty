package com.himanshoe.charty.linearregression.config

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.point.cofig.PointType

data class LinearRegressionConfig(
    val pointType: PointType,
    val strokeSize: Dp = 5.dp
)

internal object LinearRegressionDefaults {

    fun linearRegressionDefaults() = LinearRegressionConfig(
        pointType = PointType.Stroke,
        strokeSize = 5.dp
    )
}