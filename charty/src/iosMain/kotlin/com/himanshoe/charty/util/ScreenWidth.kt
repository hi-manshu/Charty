package com.himanshoe.charty.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenWidth(): Float {
    return LocalWindowInfo.current.containerSize.width.toFloat()
}