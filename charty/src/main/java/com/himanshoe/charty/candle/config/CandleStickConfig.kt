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

/**
 * Configuration options for a candlestick chart.
 *
 * @param positiveColor The color of positive (rising) candles.
 * @param negativeColor The color of negative (falling) candles.
 * @param wickColor The color of the wicks (lines) connecting the candle bodies.
 * @param canCandleScale Whether the candles can be scaled.
 * @param wickWidthScale The scaling factor for the width of the wicks, relative to the candle width.
 * @property backgroundColors The background colors of the candles chart.
 */
@Immutable
data class CandleStickConfig(
    val positiveColor: Color,
    val negativeColor: Color,
    val wickColor: Color,
    val canCandleScale: Boolean,
    val wickWidthScale: Float = 0.05f,
    val backgroundColors: List<Color> = emptyList(),
)
