/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.circle.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

/**
 * Configuration options for the labels in a circle chart.
 *
 * @param textSize The size of the text.
 * @param fontStyle The style of the font.
 * @param fontWeight The weight of the font.
 * @param fontFamily The family of the font.
 * @param indicatorSize The size of the label indicator.
 * @param maxLine The maximum number of lines for the label text.
 * @param overflow The text overflow behavior.
 */
@Immutable
data class CircleChartLabelTextConfig(
    val textSize: TextUnit,
    val fontStyle: FontStyle? = null,
    val fontWeight: FontWeight? = null,
    val fontFamily: FontFamily? = null,
    val indicatorSize: Dp = 10.dp,
    val maxLine: Int = 1,
    val overflow: TextOverflow = TextOverflow.Ellipsis
)
