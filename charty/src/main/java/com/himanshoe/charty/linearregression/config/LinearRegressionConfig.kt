package com.himanshoe.charty.linearregression.config

import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.point.cofig.PointType

/**
 * A data class that establishes the properties of a LinearRegressionChart.
 *
 * Use this class to determine the size and shape of the data plotted in the chart.
 *
 * @param pointType the [PointType] to be applied to scatter data.
 * @param lineCap the [StrokeCap] to be applied to the regression line.
 * @param strokeSize the stroke size in [Dp] to apply to the regression line.
 * @param pointSize the radius in [Dp] of the scatter points.
 */
data class LinearRegressionConfig(
    val pointType: PointType = PointType.Stroke,
    val lineCap: StrokeCap = StrokeCap.Round,
    val strokeSize: Dp = 5.dp,
    val pointSize: Dp = 5.dp
)

internal object LinearRegressionDefaults {

    fun linearRegressionDefaults() = LinearRegressionConfig(
        pointType = PointType.Stroke,
        lineCap = StrokeCap.Round,
        strokeSize = 5.dp,
        pointSize = 5.dp
    )
}
