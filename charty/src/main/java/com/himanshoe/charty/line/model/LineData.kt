/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.line.model

import androidx.compose.runtime.Immutable
import com.himanshoe.charty.common.ChartData

@Immutable
data class LineData(override val yValue: Float, override val xValue: Any) : ChartData {
    override val chartString: String
        get() = "Line Chart"
}
