package com.himanshoe.charty.common.label

/**
 * A wrapper for [Float].
 *
 * This class limits possible [Float] values to values in the range of 0f to 1f.
 *
 * @property factor a [Float] value that must be between 0f and 1f.
 */
@JvmInline
value class Multiplier(val factor: Float) {
    init {
        require(factor in 0f..1f) {
            "factor must be greater than equal to 0 and less than or equal to 1: $factor"
        }
    }
}
