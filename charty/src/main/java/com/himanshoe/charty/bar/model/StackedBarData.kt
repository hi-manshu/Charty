package com.himanshoe.charty.bar.model

import androidx.compose.ui.graphics.Color

data class StackedBarData(
    val barData: List<BarData>,
    val colors: List<Color> = List(barData.count()) { Color.Transparent }
)

internal fun List<StackedBarData>.totalItems(): Int = this.count()

