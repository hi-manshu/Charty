package com.himanshoe.charty

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform