package com.himanshoe.charty.circle.model

sealed class StartingPosition(open val angle: Float) {
    data object Top : StartingPosition(angle = 270f)
    data object Bottom : StartingPosition(angle = 90f)
    data object Left : StartingPosition(angle = 180f)
    data object Right : StartingPosition(angle = 0f)
    data class Custom(override val angle: Float) : StartingPosition(angle)
}
