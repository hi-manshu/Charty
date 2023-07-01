/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.bar.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.himanshoe.charty.common.ChartData

@Immutable
data class BarData(override val yValue: Float, override val xValue: Any, val color: Color? = null) :
    ChartData {
    override val chartString: String
        get() = "Bar Chart"
}
