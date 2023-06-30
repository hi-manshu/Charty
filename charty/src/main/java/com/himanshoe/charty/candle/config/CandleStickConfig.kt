/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.candle.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class CandleStickConfig(
    val positiveColor: Color,
    val negativeColor: Color,
    val wickColor: Color,
    val canCandleScale: Boolean,
    val wickWidthScale: Float = 0.05f,
)
