/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.common.config

import androidx.compose.runtime.Immutable

/**
 * Represents the start angle for a circular chart.
 */
@Immutable
sealed class StartAngle(open val angle: Float) {
    /**
     * Start angle of 0 degrees.
     */
    object Zero : StartAngle(0F)

    /**
     * Start angle of 180 degrees (straight angle).
     */
    object StraightAngle : StartAngle(180F)

    /**
     * Start angle of 270 degrees (reflex angle).
     */
    object ReflexAngle : StartAngle(270F)

    /**
     * Start angle of 90 degrees (right angle).
     */
    object RightAngle : StartAngle(90F)

    /**
     * Custom start angle specified by the given angle in degrees.
     *
     * @param angle The custom start angle in degrees.
     */
    data class CustomAngle(override val angle: Float) : StartAngle(angle)
}
