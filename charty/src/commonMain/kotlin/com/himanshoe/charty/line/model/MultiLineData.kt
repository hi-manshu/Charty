package com.himanshoe.charty.line.model

import com.himanshoe.charty.line.config.LineChartColorConfig

data class MultiLineData(
    val data: List<LineData>,
    val colorConfig: LineChartColorConfig
)
