/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.bubble.model

import androidx.compose.runtime.Immutable
import com.himanshoe.charty.common.ChartData

/**
 * Data class representing a bubble in a bubble chart.
 *
 * @param xValue The value of the bubble on the x-axis. It can be of any type (e.g., String, Int, Float, etc.).
 * @param yValue The value of the bubble on the y-axis.
 * @param volumeSize The size of the bubble, typically representing a third variable.
 */
@Immutable
data class BubbleData(
    override val xValue: Any,
    override val yValue: Float,
    val volumeSize: Float,
) : ChartData {
    override val chartString: String
        get() = "Bubble Chart"
}
