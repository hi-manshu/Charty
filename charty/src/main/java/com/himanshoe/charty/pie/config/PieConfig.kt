package com.himanshoe.charty.pie.config

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

data class PieData(
    val data: Float,
    val color: Color = generateRandomColor()
)

data class PieConfig(
    val isDonut: Boolean = true,
    val expandDonutOnClick: Boolean = true,
)

internal object PieConfigDefaults {
    fun pieConfigDefaults() = PieConfig(
        isDonut = true,
        expandDonutOnClick = true
    )
}

private fun generateRandomColor(): Color {
    return Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
}
