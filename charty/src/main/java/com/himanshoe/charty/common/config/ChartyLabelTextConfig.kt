/*
 * **************
 *  Charty Library : Android
 *
 *  Copyright (c) 2023. Charty Contributor
 * **************
 */

package com.himanshoe.charty.common.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

/**
 * Represents the configuration for the text labels in a chart.
 *
 * @property textSize The size of the text labels.
 * @property textColor The color of the text labels.
 * @property fontStyle The style of the font (e.g., italic). Defaults to null.
 * @property fontWeight The weight of the font (e.g., bold). Defaults to null.
 * @property fontFamily The family of the font (e.g., serif). Defaults to null.
 * @property indicatorSize The size of the indicator associated with the labels. Defaults to 10.dp.
 * @property maxLine The maximum number of lines the label can span. Defaults to 1.
 * @property overflow The behavior when the text exceeds the available space (e.g., ellipsis). Defaults to TextOverflow.Ellipsis.
 */
@Immutable
data class ChartyLabelTextConfig(
    val textSize: TextUnit,
    val textColor: Color,
    val fontStyle: FontStyle? = null,
    val fontWeight: FontWeight? = null,
    val fontFamily: FontFamily? = null,
    val indicatorSize: Dp = 10.dp,
    val maxLine: Int = 1,
    val overflow: TextOverflow = TextOverflow.Ellipsis
)
