package com.himanshoe.charty.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
actual fun getScreenWidth(): Float {
    val wDp = LocalConfiguration.current.screenWidthDp.dp
    return with(LocalDensity.current) { wDp.roundToPx().toFloat() }
}
