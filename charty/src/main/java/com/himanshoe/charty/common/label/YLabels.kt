package com.himanshoe.charty.common.label

import android.graphics.Paint.Align
import androidx.compose.ui.graphics.Color

/**
 * A data class that establishes the label and guideline properties for the y-axis.
 *
 * @param fontColor the [Color] to apply to the labels. To change the color of the guidelines, refer to [AxisConfig].
 * @param fontSize label font size as [Float].
 * @param rangeAdjustment adjusts the data range by [Multiplier].
 * Adjusting this value determines the distance between the x-axis and the minimum y value.
 * @param maxValueAdjustment adjusts the max y value of the data by [Multiplier].
 * Increasing this value alone will shift data in the chart downwards on the canvas.
 * @param minValueAdjustment adjusts the minimum y value of the data by [Multiplier].
 * Increasing this value alone will shift data in the chart upwards on the canvas.
 * @param isBaseZero when true, y labels will be start from 0.
 * @param breaks the [Int] number of breaks to apply to the y-axis.
 * @param yOffset the amount as a [Float] to vertically offset the y labels.
 * @param xOffset the amount as a [Float] to offset y labels from the y-axis.
 * The greater the value, the further from the axis.
 * Negative values will move the labels to the right of the y-axis.
 * @param textAlignment uses [Align] to justify the labels.
 * @param rotation [Float] number of degrees to rotate the labels. When rotated, the axis will automatically
 * center the labels according to their top right point (from 0 degrees).
 * Using negatives values is recommended when rotating y labels.
 * @param lineAlpha the [Multiplier] value to apply as alpha to the y guidelines.
 * @param lineStartPadding the value as a [Float] to apply as a start padding to the y guidelines.
 */
data class YLabels(
    val fontColor: Color = Color.Black,
    val fontSize: Float = 24f,
    val rangeAdjustment: Multiplier = Multiplier(0f),
    val maxValueAdjustment: Multiplier = Multiplier(0f),
    val minValueAdjustment: Multiplier = Multiplier(0f),
    val isBaseZero: Boolean = false,
    val breaks: Int = 5,
    val xOffset: Float = 25f,
    val yOffset: Float = 0f,
    val textAlignment: Align = Align.CENTER,
    val rotation: Float = 0f,
    val lineAlpha: Multiplier = Multiplier(.1f),
    val lineStartPadding: Float = 25f
)

internal object YLabelsDefaults {
    fun yLabelsDefaults() = YLabels()
}
