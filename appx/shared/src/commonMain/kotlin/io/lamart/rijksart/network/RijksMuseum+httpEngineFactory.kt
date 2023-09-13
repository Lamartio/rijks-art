package io.lamart.rijksart.network

import io.ktor.client.engine.HttpClientEngineFactory

internal expect val RijksMuseum.Companion.httpEngineFactory: HttpClientEngineFactory<*>