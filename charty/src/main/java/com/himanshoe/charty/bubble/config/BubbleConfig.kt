package com.himanshoe.charty.bubble.config

data class BubbleConfig(
    val maxVolumeSize: Float
)

internal object BubbleConfigDefaults {

    fun bubbleConfigDefaults() = BubbleConfig(
        maxVolumeSize = 100F
    )
}
