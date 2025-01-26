package com.himanshoe.charty

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String = "Hello, ${platform.name}!"
}
