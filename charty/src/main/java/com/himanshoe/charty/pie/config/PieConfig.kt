package com.himanshoe.charty.pie.config

import androidx.compose.ui.graphics.Color
import kotlin.random.Random


data class PieConfig(
    val colors: List<Color>,
)

internal object PieConfigDefaults {

    fun pieConfigDefaults(count: Int) = PieConfig(
        colors = generateRandomColor(count),
    )
}

private fun generateRandomColor(count: Int): List<Color> {
    val colors = mutableListOf<Color>()
    repeat(count) {
        val color = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
        colors.add(color)
    }
    return colors.toList()
}
