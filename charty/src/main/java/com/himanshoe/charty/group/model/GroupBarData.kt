/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.group.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class GroupBarData(
    val label: String,
    val dataPoints: List<Float>,
    val colors: List<Color> = List(dataPoints.count()) { Color.Transparent }
)
