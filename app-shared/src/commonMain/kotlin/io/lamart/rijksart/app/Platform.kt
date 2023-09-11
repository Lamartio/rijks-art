package io.lamart.rijksart.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform