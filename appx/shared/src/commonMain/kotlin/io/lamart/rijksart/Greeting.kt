package io.lamart.rijksart

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    fun bye(): String {
        return "Hello, ${platform.name}!"
    }

    fun yeah(): String {
        return "Yeah, ${platform.name}!"
    }

    fun nope(): String {
        return "Yeah, ${platform.name}!"
    }
}