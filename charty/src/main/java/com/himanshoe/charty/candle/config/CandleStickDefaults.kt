/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.candle.config

import androidx.compose.ui.graphics.Color

object CandleStickDefaults {

    fun defaultCandleStickConfig() = CandleStickConfig(
        Color.Green,
        Color.Red,
        Color.Black,
        false
    )
}
