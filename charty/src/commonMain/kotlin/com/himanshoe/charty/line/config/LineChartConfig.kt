package com.himanshoe.charty.line.config

import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap

data class LineChartConfig(
    val axisLineWidth: Float = 2f,
    val gridLineWidth: Float = 1f,
    val lineChartStrokeWidth: Float = 5f,
    val gridLinePathEffect: PathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
    val lineCap: StrokeCap = StrokeCap.Round,
)