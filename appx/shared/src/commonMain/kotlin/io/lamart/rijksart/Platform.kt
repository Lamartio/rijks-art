package io.lamart.rijksart

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform