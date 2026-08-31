package com.himanshoe.charty.radar.internal

import androidx.compose.ui.geometry.Offset
import kotlin.math.PI

private const val LABEL_OFFSET_HALF = 2f
private const val ANGLE_QUARTER = PI / 4
private const val ANGLE_THREE_QUARTERS = 3 * PI / 4
private const val ANGLE_MINUS_QUARTER = -PI / 4
private const val ANGLE_MINUS_THREE_QUARTERS = -3 * PI / 4

/**
 * How a label-sized box shifts off its anchor so it sits outside the chart on every side: centred
 * above or below for the top and bottom axes, pushed left or right for the sides. Shared between the
 * label pass and the value stack that hangs beneath it, so the two can never disagree about where a
 * label is.
 */
internal fun radarLabelBoxAlignment(
    angle: Float,
    textWidth: Float,
    textHeight: Float,
): Offset {
    val isBottom = angle > ANGLE_QUARTER && angle < ANGLE_THREE_QUARTERS
    val isTop = angle > ANGLE_MINUS_THREE_QUARTERS && angle < ANGLE_MINUS_QUARTER
    val isRight = angle >= ANGLE_MINUS_QUARTER && angle <= ANGLE_QUARTER

    val offsetX =
        when {
            isBottom || isTop -> -textWidth / LABEL_OFFSET_HALF
            isRight -> 0f
            else -> -textWidth
        }

    val offsetY =
        when {
            isBottom -> 0f
            isTop -> -textHeight
            else -> -textHeight / LABEL_OFFSET_HALF
        }

    return Offset(x = offsetX, y = offsetY)
}
