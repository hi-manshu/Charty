package com.himanshoe.charty.bar

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import com.himanshoe.charty.bar.config.ImpulseBarConfig
import com.himanshoe.charty.common.ChartColor
import com.himanshoe.charty.common.asSolidChartColor
import kotlinx.coroutines.delay

/**
 * A composable function that displays an impulse progress bar.
 *
 * @param progress A lambda function that returns the current progress as a Float.
 * @param impulse A lambda function that returns the current impulse as a Float.
 * @param modifier A Modifier for this composable.
 * @param maxProgress The maximum progress value. Default is 100F.
 * @param totalBlocks The total number of blocks in the progress bar. Default is 10.
 * @param impulseBarConfig Configuration for the impulse bar, including styles and colors.
 * @param trackColor The color of the track (unfilled part) of the progress bar.
 * @param progressColor The color of the filled part of the progress bar.
 */
@Composable
fun ImpulseProgressBar(
    progress: () -> Float,
    impulse: () -> Float,
    modifier: Modifier = Modifier,
    maxProgress: Float = 100F,
    totalBlocks: Int = 10,
    impulseBarConfig: ImpulseBarConfig = ImpulseBarConfig.default(),
    trackColor: ChartColor = Color.Gray.asSolidChartColor(),
    progressColor: ChartColor = Color.Green.asSolidChartColor(),
) {
    ImpulseProgressBarContent(
        progress = progress,
        totalBlocks = totalBlocks,
        trackColor = trackColor,
        impulseBarConfig = impulseBarConfig,
        progressColor = progressColor,
        modifier = modifier,
        maxProgress = maxProgress,
        impulse = impulse,
    )
}

/**
 * A composable function that displays an impulse progress bar.
 *
 * @param progress A lambda function that returns the current progress as an Int.
 * @param impulse A lambda function that returns the current impulse as an Int.
 * @param modifier A Modifier for this composable.
 * @param maxProgress The maximum progress value. Default is 100.
 * @param totalBlocks The total number of blocks in the progress bar. Default is 10.
 * @param impulseBarConfig Configuration for the impulse bar, including styles and colors.
 * @param trackColor The color of the track (unfilled part) of the progress bar.
 * @param progressColor The color of the filled part of the progress bar.
 */
@Composable
fun ImpulseProgressBar(
    progress: () -> Int,
    impulse: () -> Int,
    modifier: Modifier = Modifier,
    maxProgress: Int = 100,
    totalBlocks: Int = 10,
    impulseBarConfig: ImpulseBarConfig = ImpulseBarConfig.default(),
    trackColor: ChartColor = Color.Gray.asSolidChartColor(),
    progressColor: ChartColor = Color.Green.asSolidChartColor(),
) {
    ImpulseProgressBar(
        progress = { progress().toFloat() },
        impulse = { impulse().toFloat() },
        modifier = modifier,
        maxProgress = maxProgress.toFloat(),
        totalBlocks = totalBlocks,
        impulseBarConfig = impulseBarConfig,
        trackColor = trackColor,
        progressColor = progressColor
    )
}

@Composable
private fun ImpulseProgressBarContent(
    progress: () -> Float,
    totalBlocks: Int,
    trackColor: ChartColor,
    impulseBarConfig: ImpulseBarConfig,
    progressColor: ChartColor,
    maxProgress: Float,
    impulse: () -> Float,
    modifier: Modifier = Modifier,
) {
    val alphaAnimation = remember { Animatable(0f) }
    val textMeasurer = rememberTextMeasurer()

    LaunchedEffect(progress()) {
        if (impulseBarConfig.canAnimate) {
            delay(200)
            alphaAnimation.animateTo(progress())
        } else {
            alphaAnimation.snapTo(progress())
        }
    }
    Canvas(modifier = modifier) {
        // Calculate the number of filled blocks and the fraction of the partial block
        val filledBlocks = (alphaAnimation.value / maxProgress * totalBlocks).toInt()
        val partialBlockFraction = (alphaAnimation.value / maxProgress * totalBlocks) - filledBlocks

        // Calculate the width of each block and the gap between blocks
        val blockWidth =
            size.width / (totalBlocks + (totalBlocks - 1) * impulseBarConfig.blockGapRatio)
        val gap = blockWidth * impulseBarConfig.blockGapRatio

        val baseBlockHeight = size.height

        // Calculate the index of the block that should have the impulse effect
        val impulseValue = impulse() - 1
        val normalizedImpulse = impulseValue / maxProgress
        val rawImpulseIndex = (normalizedImpulse * totalBlocks).toInt()
        val impulseBlockIndex = rawImpulseIndex.coerceAtMost(totalBlocks - 1)

        for (index in 0 until totalBlocks) {
            // Calculate the x offset for the current block
            val xOffset = index * (blockWidth + gap)

            // Determine if the current block is the impulse block
            val isImpulseBlock = index == impulseBlockIndex

            // Calculate the height and top offset for the current block
            val blockHeight =
                if (isImpulseBlock) baseBlockHeight * impulseBarConfig.impulseBlockHeightMultiplier else baseBlockHeight
            val topLeft = Offset(xOffset, if (isImpulseBlock) -blockHeight * 0.1f else 0f)

            val blockSize = Size(blockWidth, blockHeight)

            when {
                index < filledBlocks -> {
                    drawRoundRect(
                        brush = Brush.linearGradient(progressColor.value),
                        topLeft = topLeft,
                        size = blockSize,
                        cornerRadius = impulseBarConfig.cornerRadius,
                    )
                }

                index == filledBlocks -> {
                    drawRoundRect(
                        brush = Brush.linearGradient(progressColor.value),
                        cornerRadius = impulseBarConfig.cornerRadius,
                        topLeft = topLeft.copy(x = xOffset),
                        size = blockSize.copy(width = blockWidth * partialBlockFraction)
                    )
                    drawRoundRect(
                        brush = Brush.linearGradient(trackColor.value),
                        cornerRadius = impulseBarConfig.cornerRadius,
                        topLeft = topLeft,
                        size = blockSize.copy(width = blockWidth * (1 - partialBlockFraction))
                    )
                }

                else -> {
                    drawRoundRect(
                        cornerRadius = impulseBarConfig.cornerRadius,
                        brush = Brush.linearGradient(trackColor.value),
                        topLeft = topLeft,
                        size = blockSize
                    )
                }
            }
            if (impulseBarConfig.showTextLabel && isImpulseBlock || index == totalBlocks - 1) {
                val text = if (isImpulseBlock) {
                    progress()
                } else {
                    maxProgress
                }
                val textLayoutResult = textMeasurer.measure(
                    text = text.toString(),
                    style = impulseBarConfig.textStyle
                )
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(xOffset, topLeft.y + blockHeight + gap)
                )
            }
        }
    }
}
