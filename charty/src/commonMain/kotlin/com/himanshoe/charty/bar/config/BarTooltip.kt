package com.himanshoe.charty.bar.config

sealed interface BarTooltip {

    data object BarTop : BarTooltip
    data object GraphTop : BarTooltip
}
