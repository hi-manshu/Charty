package com.himanshoe.charty.circle.config

import com.himanshoe.charty.circle.model.StartingPosition

data class CircleChartConfig(
    val showEndIndicator: Boolean,
    val startingPosition: StartingPosition
) {
    companion object {
        fun default() = CircleChartConfig(
            showEndIndicator = true,
            startingPosition = StartingPosition.Bottom
        )
    }
}
