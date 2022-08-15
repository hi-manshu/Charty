package com.himanshoe.charty.horizontalbar.model

import androidx.compose.ui.graphics.Color

data class GroupedHorizontalBarData(
    val horizontalBarData: List<HorizontalBarData>,
    val colors: List<Color> = List(horizontalBarData.count()) { Color.Transparent }
)

internal fun List<GroupedHorizontalBarData>.totalItems(): Int = this.sumOf {
    it.horizontalBarData.count()
}

internal fun List<GroupedHorizontalBarData>.maxXValue() = maxOf {
    it.horizontalBarData.maxXValue()
}
