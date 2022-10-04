package com.himanshoe.charty.candle

import android.graphics.Typeface
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.candle.config.CandleStickConfig
import com.himanshoe.charty.candle.config.CandleStickDefaults
import com.himanshoe.charty.candle.model.CandleEntry
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.axis.AxisConfigDefaults
import com.himanshoe.charty.common.axis.drawYAxisWithLabels

@Composable
fun CandleStickChart(
    candleEntryData: List<CandleEntry>,
    modifier: Modifier = Modifier,
    axisConfig: AxisConfig = AxisConfigDefaults.axisConfigDefaults(),
    candleStickConfig: CandleStickConfig = CandleStickDefaults.candleStickDefaults()
) {
    val x = remember { Animatable(0f) }
    val maxYValue = candleEntryData.maxOf { it.high }
    val targetValue = if (candleStickConfig.totalCandles == 0) candleEntryData.count()
        .toFloat() else candleStickConfig.totalCandles.toFloat()

    LaunchedEffect(true) {
        x.animateTo(
            targetValue = targetValue,
            animationSpec = tween(
                durationMillis = if (candleStickConfig.shouldAnimateCandle) 3000 else 0,
                easing = FastOutLinearInEasing
            ),
        )
    }
    val hasPadding = axisConfig.showAxis
    Canvas(
        modifier = modifier
            .padding(
                start = if (hasPadding) 40.dp else 10.dp,
                top = if (hasPadding) 20.dp else 0.dp
            )
            .drawBehind {
                if (axisConfig.showAxis) {
                    drawYAxisWithLabels(axisConfig, maxYValue, true)
                }
            }
            .padding(start = if (hasPadding) 20.dp else 0.dp)

    ) {
        val xBounds = Pair(0f, candleEntryData.count().toFloat())
        val yBounds = getBounds(candleEntryData)
        val scaleX = size.width.div(xBounds.second.minus(xBounds.first))
        val scaleY = size.height.div(yBounds.second.minus(yBounds.first))
        val yMove = yBounds.first.times(scaleY)
        val interval = (0.rangeTo(kotlin.math.min(candleEntryData.size.minus(1), x.value.toInt())))
        val last = interval.last()

        interval.forEach { value ->
            val xPoint = value.times(scaleX)
            val yPointH = (size.height.minus(candleEntryData[value].high.times(scaleY))).plus(yMove)
            val yPointL = (size.height.minus(candleEntryData[value].low.times(scaleY))).plus(yMove)
            val yPointO = (size.height.minus(candleEntryData[value].opening.times(scaleY))).plus(yMove)
            val yPointC = (size.height.minus(candleEntryData[value].closing.times(scaleY))).plus(yMove)
            val path1 = Path()
            val path2 = Path()
            val isPositive = candleEntryData[value].opening <= candleEntryData[value].closing
            val candleColor = if (isPositive) candleStickConfig.positiveColor else candleStickConfig.negativeColor
            val candleLineColor = if (isPositive) candleStickConfig.positiveCandleLineColor else candleStickConfig.negativeCandleLineColor

            path1.moveTo(xPoint, yPointH)
            path1.lineTo(xPoint, yPointL)
            drawPath(
                path = path1,
                color = candleLineColor,
                style = Stroke(candleStickConfig.highLowLineWidth)
            )

            path2.moveTo(xPoint, yPointO)
            path2.lineTo(xPoint, yPointC)
            drawPath(
                path = path2,
                color = candleColor,
                style = Stroke(scaleX.minus(scaleX.div(8)))
            )
        }

        if (candleStickConfig.showPriceText) {
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    candleEntryData[last].closing.toString(),
                    (last * scaleX) + 50f, // x-coordinates of the origin (top left)
                    size.height - (candleEntryData[last].closing * scaleY) + yMove + 20f, // y-coordinates of the origin (top left)
                    textPaint(candleStickConfig.textColor, size.width.div(30))
                )
            }
        }
    }
}

private fun textPaint(textColor: Color = Color.Blue, textSizeValue: Float) =
    Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = textSizeValue
        color = textColor.toArgb()
        typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
    }

private fun getBounds(candleEntryList: List<CandleEntry>): Pair<Float, Float> {
    var min = Float.MAX_VALUE
    var max = -Float.MAX_VALUE
    candleEntryList.forEach {
        min = min.coerceAtMost(it.low)
        max = max.coerceAtLeast(it.high)
    }
    return Pair(min, max)
}
