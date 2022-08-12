package com.himanshoe.charty.bar.model

import androidx.compose.ui.graphics.Color

data class GroupedBarData(val barData: List<BarData>, val colors: List<Color> = List(barData.count()) { Color.Transparent })

internal fun List<GroupedBarData>.totalItems(): Int = this.sumOf {
    it.barData.count()
}

internal fun List<GroupedBarData>.maxYValue() = maxOf {
    it.barData.maxYValue()
}
