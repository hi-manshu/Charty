/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.candle.model

import androidx.compose.runtime.Immutable

/**
 * Data class representing a single candlestick in a candlestick chart.
 *
 * @param high The highest value of the candlestick.
 * @param low The lowest value of the candlestick.
 * @param open The opening value of the candlestick.
 * @param close The closing value of the candlestick.
 */
@Immutable
data class CandleData(
    val high: Float,
    val low: Float,
    val open: Float,
    val close: Float
)
