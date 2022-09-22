package com.himanshoe.charty.bubble.model

data class BubbleData(
    val xValue: Any,
    val yValue: Float,
    val volumeSize: Float,
)

internal fun List<BubbleData>.maxYValue() = maxOf {
    it.yValue
}

internal fun List<BubbleData>.maxVolumeSize() = maxOf {
    it.volumeSize
}
