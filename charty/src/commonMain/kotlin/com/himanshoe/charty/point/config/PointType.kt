/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.point.config

import androidx.compose.runtime.Immutable

/**
 * Represents the type of a point in a chart.
 */
sealed interface PointType {
    /**
     * A filled point type.
     */
    object Fill : PointType

    /**
     * A stroke point type with a specified stroke width.
     *
     * @param strokeWidth The stroke width for the point.
     */
    @Immutable
    data class Stroke(val strokeWidth: Float = 4F) : PointType
}
