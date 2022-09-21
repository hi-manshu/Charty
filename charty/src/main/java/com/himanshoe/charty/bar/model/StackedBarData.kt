package com.himanshoe.charty.bar.model

data class StackedBarData(
    val xValue: Any,
    val yValue: List<Float>,
)

internal fun List<StackedBarData>.isValid(count: Int) = totalItems() == count

internal fun List<StackedBarData>.totalItems(): Int = this.maxOf {
    it.yValue.count()
}

internal fun List<StackedBarData>.maxYValue() = maxOf {
    it.yValue.sum()
}
