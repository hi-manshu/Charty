package com.himanshoe.charty.common.label

import android.graphics.Paint.Align
import androidx.compose.ui.graphics.Color

/**
 * A data class that establishes the label and guideline properties for the x-axis.
 *
 * @param fontColor the [Color] to apply to the labels. To change the color of the guidelines, refer to [AxisConfig].
 * @param fontSize label font size as [Float].
 * @param rangeAdjustment adjusts the data range by [Multiplier].
 * Adjusting this value determines the distance between the y-axis and the minimum x value.
 * @param breaks the [Int] number of breaks to apply to the x-axis.
 * @param yOffset the amount as a [Float] to offset x labels from the x-axis.
 * The greater the value, the further from the axis.
 * Negative values will move the labels above the x-axis.
 * @param xOffset the amount as a [Float] to horizontally offset the x labels.
 * @param textAlignment uses [Align] to justify the labels.
 * @param rotation [Float] number of degrees to rotate the labels. When rotated, the axis will automatically
 * center the labels according to their top left point (from 0 degrees).
 * @param lineAlpha the [Multiplier] value to apply as alpha to the x guidelines.
 * @param showLines used to determine whether the canvas should draw guidelines for the x labels.
 */
data class XLabels(
    val fontColor: Color = Color.Black,
    val fontSize: Float = 24f,
    val rangeAdjustment: Multiplier = Multiplier(.1f),
    val breaks: Int = 5,
    val yOffset: Float = 50f,
    val xOffset: Float = 0f,
    val textAlignment: Align = Align.CENTER,
    val rotation: Float = 0f,
    val lineAlpha: Multiplier = Multiplier(.1f),
    val showLines: Boolean = false
)

internal object XLabelsDefaults {
    fun xLabelsDefaults() = XLabels()
}
