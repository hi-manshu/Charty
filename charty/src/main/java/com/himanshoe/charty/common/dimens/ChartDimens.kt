package com.himanshoe.charty.common.dimens

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ChartDimens(
    val horizontalPadding: Dp
)

internal object ChartDimensDefaults {

    fun chartDimesDefaults() = ChartDimens(
        horizontalPadding = 4.dp
    )
}
