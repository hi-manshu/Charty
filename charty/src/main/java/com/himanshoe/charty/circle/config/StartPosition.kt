package com.himanshoe.charty.circle.config


sealed class StartPosition(val angle: Float) {
    object Top : StartPosition(270F)
    object Right : StartPosition(0F)
    object Bottom : StartPosition(900F)
    object End : StartPosition(180F)
    data class Custom(val customAngle: Float) : StartPosition(customAngle)
}
