package io.lamart.rijksart.app

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    fun bye(): String {
        return "Bye, ${platform.name}!"
    }
}