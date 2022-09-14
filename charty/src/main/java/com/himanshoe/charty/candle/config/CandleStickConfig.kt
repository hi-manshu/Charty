package com.himanshoe.charty.candle.config

import androidx.compose.ui.graphics.Color

data class CandleStickConfig(
    val positiveColor: Color,
    val negativeColor: Color,
    val positiveCandleLineColor: Color = positiveColor,
    val negativeCandleLineColor: Color = negativeColor,
    val textColor: Color,
    val shouldAnimateCandle: Boolean = true,
    val showPriceText: Boolean = true,
    val highLowLineWidth: Float,
    val totalCandles: Int = 0
)

internal object CandleStickDefaults {

    fun candleStickDefaults() = CandleStickConfig(
        positiveColor = Color.Green,
        negativeColor = Color.Red,
        textColor = Color.Black,
        highLowLineWidth = 4f
    )
}
