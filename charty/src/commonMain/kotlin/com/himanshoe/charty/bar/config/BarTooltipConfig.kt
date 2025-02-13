package com.himanshoe.charty.bar.config

sealed interface BarTooltip {

    data object Default : BarTooltip
    data object GraphTop : BarTooltip
}