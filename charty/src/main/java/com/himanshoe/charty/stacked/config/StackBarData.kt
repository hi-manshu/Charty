/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.stacked.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class StackBarData(
    val label: String,
    val dataPoints: List<Float>,
    val colors: List<Color> = List(dataPoints.count()) { Color.Transparent }
)
