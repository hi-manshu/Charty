package com.himanshoe.charty.common.axis

@JvmInline
value class Multiplier(val factor: Float) {
    init {
        require(factor in 0f..1f) {
            "factor must be greater than equal to 0 and less than or equal to 1: $factor"
        }
    }
}