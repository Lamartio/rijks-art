package io.lamart.rijksart.network

import io.ktor.client.engine.HttpClientEngineFactory

internal expect val httpFactory: HttpClientEngineFactory<*>
