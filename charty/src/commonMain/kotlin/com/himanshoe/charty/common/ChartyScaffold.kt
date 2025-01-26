package com.himanshoe.charty.common

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun ChartyScaffold(
    modifier: Modifier = Modifier,
    innerPadding: Dp = 4.dp,
    content: @Composable () -> Unit = {},
) {
    BoxWithConstraints(
        modifier =
        modifier.padding(
            start = innerPadding.times(2),
            bottom = innerPadding.times(2),
            top = innerPadding.times(2),
            end = innerPadding,
        ),
    ) {
        content()
    }
}
