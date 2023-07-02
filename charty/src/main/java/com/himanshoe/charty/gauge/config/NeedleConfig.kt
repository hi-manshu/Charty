/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.gauge.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Configuration for the needle in the gauge chart.
 *
 * @property color The color of the needle.
 * @property strokeWidth The width of the needle stroke.
 */
@Immutable
data class NeedleConfig(
    val color: Color,
    val strokeWidth: Float,
)
