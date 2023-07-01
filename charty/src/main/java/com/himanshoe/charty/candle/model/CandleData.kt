/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.candle.model

import androidx.compose.runtime.Immutable

@Immutable
data class CandleData(
    val high: Float,
    val low: Float,
    val open: Float,
    val close: Float
)
