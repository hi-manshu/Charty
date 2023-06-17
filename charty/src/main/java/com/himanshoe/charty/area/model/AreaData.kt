package com.himanshoe.charty.area.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class AreaData(
    val xValue: Any,
    val points: List<Float>,
    val color: Color
)
