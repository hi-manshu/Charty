/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.bubble.model

import com.himanshoe.charty.common.ChartData

data class BubbleData(
    override val xValue: Any,
    override val yValue: Float,
    val volumeSize: Float,
) : ChartData {
    override val chartString: String
        get() = "Bubble Chart"
}
