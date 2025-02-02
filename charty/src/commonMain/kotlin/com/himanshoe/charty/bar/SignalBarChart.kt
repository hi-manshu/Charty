package com.himanshoe.charty.bar

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.common.asSolidChartColor
import kotlinx.coroutines.delay


/**
 * A composable function that displays a signal bar chart with solid colors.
 *
 * @param progress A lambda function that returns the current progress as a Float.
 * @param modifier A Modifier to be applied to the Canvas.
 * @param trackColor The color to be used for the track.
 * @param progressColor The color to be used for the progress.
 * @param gapRatio A Float representing the ratio of the gap between blocks.
 */
@Composable
fun SignalProgressBarChart(
    progress: () -> Float,
    modifier: Modifier = Modifier,
    totalBlocks: Int = 10,
    trackColor: ChartColor = Color.Gray.asSolidChartColor(),
    progressColor: ChartColor = Color.Green.asSolidChartColor(),
    gapRatio: Float = 0.1F
) {
    SignalBarChartContent(
        progress = progress,
        totalBlocks = totalBlocks,
        trackColorBrush = Brush.linearGradient(trackColor.value),
        progressColorBrush = Brush.linearGradient(progressColor.value),
        gapRatio = gapRatio,
        modifier = modifier
    )
}

/**
 * A private composable function that displays the content of a signal bar chart.
 *
 * @param progress A lambda function that returns the current progress as a Float.
 * @param trackColorBrush A Brush to be used for the track.
 * @param progressColorBrush A Brush to be used for the progress.
 * @param gapRatio A Float representing the ratio of the gap between blocks.
 * @param totalBlocks An Int representing the total number of blocks in the chart.
 * @param modifier A Modifier to be applied to the Canvas.
 */
@Composable
private fun SignalBarChartContent(
    progress: () -> Float,
    trackColorBrush: Brush,
    progressColorBrush: Brush,
    gapRatio: Float,
    totalBlocks: Int,
    modifier: Modifier = Modifier,
) {

    val alphaAnimation = remember { Animatable(0f) }
    LaunchedEffect(progress()) {
        delay(200)
        alphaAnimation.animateTo(progress())
    }
    Canvas(modifier = modifier) {
        val filledBlocks = (alphaAnimation.value / 100 * totalBlocks).toInt()
        val partialBlockFraction = (alphaAnimation.value / 100 * totalBlocks) - filledBlocks
        val blockHeight = size.height / (totalBlocks + (totalBlocks - 1) * gapRatio)
        val gap = blockHeight * gapRatio
        val blockWidth = size.width

        for (i in 0 until totalBlocks) {
            val yOffset = size.height - (i + 1) * (blockHeight + gap)
            val topLeft = Offset(gap, yOffset)
            val blockSize = Size(blockWidth, blockHeight)
            val cornerRadius = CornerRadius(5F)
            when {
                i < filledBlocks -> {
                    drawRoundRect(
                        brush = progressColorBrush,
                        topLeft = topLeft,
                        size = blockSize,
                        cornerRadius = cornerRadius,
                    )
                }

                i == filledBlocks -> {
                    drawRoundRect(
                        brush = progressColorBrush,
                        cornerRadius = cornerRadius,
                        topLeft = topLeft.copy(y = yOffset + blockHeight * (1 - partialBlockFraction)),
                        size = blockSize.copy(height = blockHeight * partialBlockFraction)
                    )
                    drawRoundRect(
                        brush = trackColorBrush,
                        cornerRadius = cornerRadius,
                        topLeft = topLeft,
                        size = blockSize.copy(height = blockHeight * (1 - partialBlockFraction))
                    )
                }

                else -> {
                    drawRoundRect(
                        cornerRadius = cornerRadius,
                        brush = trackColorBrush,
                        topLeft = topLeft,
                        size = blockSize
                    )
                }
            }
        }
    }
}
