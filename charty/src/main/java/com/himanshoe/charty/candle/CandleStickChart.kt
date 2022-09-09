package com.himanshoe.charty.candle

import android.graphics.Typeface
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himanshoe.charty.candle.model.CandleEntry

@Composable
fun CandleStickChart(
    candleData: List<CandleEntry>,
    positiveColor: Color,
    negativeColor: Color,
    modifier: Modifier = Modifier,
    lineColors: List<Color>,
    drawLineAtY: Float = 0f,
    lineAtYDirection: Int = 0,
    highLowLineWidth: Float = 4f,
    shouldAnimate: Boolean = true,
    shouldDrawLiveDot: Boolean = true,
    showPriceText: Boolean = true,
    customXTarget: Int = 30,
) {
    val yValues = candleData.toMutableList()
    val x = remember { Animatable(0f) }
    val xTarget =
        if (customXTarget > 0) customXTarget.toFloat() else (yValues.size.minus(1)).toFloat()

    LaunchedEffect(true) {
        x.animateTo(
            targetValue = xTarget,
            animationSpec = tween(
                durationMillis = if (shouldAnimate) 3000 else 0,
                easing = LinearEasing
            ),
        )
    }
    val infiniteTransition = rememberInfiniteTransition()
    val radius by infiniteTransition.animateFloat(
        initialValue = 7f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val opacity by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val lineAtYColor = if (lineAtYDirection == 0) lineColors.first() else lineColors.last()
    val downUpArrowText = if (lineAtYDirection == 0) "▲" else "▼"

    Canvas(
        modifier = modifier
            .padding(8.dp)
    ) {
        val xBounds = Pair(0f, xTarget)
        val yBounds = getBounds(yValues)
        val scaleX = size.width / (xBounds.second - xBounds.first)
        val scaleY = size.height / (yBounds.second - yBounds.first)
        val yMove = yBounds.first * scaleY
        val interval = (0..kotlin.math.min(yValues.size - 1, x.value.toInt()))
        val last = interval.last()
        interval.forEach { value ->

            val xPoint = value * scaleX
            val yPointH = size.height - (yValues[value].high * scaleY) + yMove
            val yPointL = size.height - (yValues[value].low * scaleY) + yMove
            var yPointO = size.height - (yValues[value].opening * scaleY) + yMove
            val yPointC = size.height - (yValues[value].closing * scaleY) + yMove
            val path1 = Path()
            val path2 = Path()
            // Start of new candle case to avoid 0 size of y axis
            if (yPointO - yPointC == 0f) {
                yPointO = yPointC + 2f
            }
            val candleColor =
                if (yValues[value].opening <= yValues[value].closing) positiveColor else negativeColor
            path1.moveTo(xPoint, yPointH)
            path1.lineTo(xPoint, yPointL)
            drawPath(
                path = path1,
                brush = Brush.linearGradient(listOf(candleColor, candleColor)),
                style = Stroke(highLowLineWidth)
            )

            path2.moveTo(xPoint, yPointO)
            path2.lineTo(xPoint, yPointC)
            drawPath(
                path = path2,
                brush = Brush.linearGradient(listOf(candleColor, candleColor)),
                style =
                Stroke(scaleX - scaleX / 8)
            )
        }

        if (shouldDrawLiveDot) {
            if (drawLineAtY > 0f) {
                val y = size.height - (drawLineAtY * scaleY) + yMove
                val xStart = 0f
                val xEnd = size.width
                drawLine(
                    lineAtYColor,
                    Offset(
                        xStart, y
                    ),
                    Offset(xEnd - 140f, y), strokeWidth = 1f,
                )
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        "$downUpArrowText$drawLineAtY",
                        xEnd - 140f,            // x-coordinates of the origin (top left)
                        y + 20f, // y-coordinates of the origin (top left)
                        textPaint(lineAtYColor)
                    )
                }
            }
            if (showPriceText) {
                drawIntoCanvas {
                    it.nativeCanvas.drawText(
                        yValues[last].closing.toString(),
                        (last * scaleX) + 50f,            // x-coordinates of the origin (top left)
                        size.height - (yValues[last].closing * scaleY) + yMove + 20f, // y-coordinates of the origin (top left)
                        textPaint(lineColors.first())
                    )
                }
            }
        }
    }
}

fun textPaint(textColor: Color = Color.Blue) = Paint().asFrameworkPaint().apply {
    isAntiAlias = true
    textSize = 48.sp.value
    color = textColor.toArgb()
    typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
}

fun getBounds(list: List<CandleEntry>): Pair<Float, Float> {
    var min = Float.MAX_VALUE
    var max = -Float.MAX_VALUE
    list.forEach {
        min = min.coerceAtMost(it.low)
        max = max.coerceAtLeast(it.high)
    }
    return Pair(min, max)
}

fun getCandleDummyData(price: Float) = CandleEntry(
    price - 100f, price + 200f, price, price + 50f
)
