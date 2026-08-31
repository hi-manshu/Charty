package com.himanshoe.charty.radar.internal

import kotlin.math.PI

private const val FULL_CIRCLE_DEGREES = 360f
private const val DEGREES_TO_RADIANS = PI.toFloat() / 180f

/**
 * The angle of one radar axis, in radians: the start angle plus this axis's even share of the
 * circle. Every radar pass — shape, labels, values, hit testing — must place an axis at the same
 * angle, so the formula gets one home instead of an inline copy per pass.
 */
internal fun radarAxisAngleRadians(
    startAngleDegrees: Float,
    axisIndex: Int,
    numberOfAxes: Int,
): Float = (startAngleDegrees + (FULL_CIRCLE_DEGREES * axisIndex / numberOfAxes)) * DEGREES_TO_RADIANS
