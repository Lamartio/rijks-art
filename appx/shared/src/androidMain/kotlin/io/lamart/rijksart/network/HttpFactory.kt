package io.lamart.rijksart.network

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

internal actual val httpFactory: HttpClientEngineFactory<*> get() = OkHttp
