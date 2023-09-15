package io.lamart.rijksart.network

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin


internal actual val RijksMuseum.Companion.httpEngineFactory: HttpClientEngineFactory<*>
    get() = Darwin