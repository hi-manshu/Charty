/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.candle.config

import androidx.compose.ui.graphics.Color

/**
 * Default configuration options for a candlestick chart.
 */
object CandleStickDefaults {
    /**
     * Returns the default configuration for a candlestick chart.
     *
     * @return The default CandleStickConfig.
     */
    fun defaultCandleStickConfig() = CandleStickConfig(
        Color.Green,
        Color.Red,
        Color.Black,
        false
    )
}
