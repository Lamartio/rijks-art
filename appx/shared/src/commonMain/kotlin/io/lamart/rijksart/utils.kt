package io.lamart.rijksart

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal inline fun <reified T> String.decode(): T? =
    runCatching<String, T>(Json::decodeFromString).getOrNull()

internal inline fun <reified T> T.encode(): String? =
    runCatching(Json::encodeToString).getOrNull()

/**
 * Will first try to return from persistence. Simultaneously it is fetching and persisting latest result and will return that after initial emission.
 */

@OptIn(ExperimentalCoroutinesApi::class)
internal fun <I, O : Any> dataFlowOf(
    get: suspend (I) -> O?,
    fetch: suspend (I) -> O,
    set: suspend (I, O) -> Unit
): (input: I) -> Flow<O> =
    { input ->
        val getting = flowOf(input)
            .map(get)
            .filterNotNull()
        val fetchingAndSetting = flowOf(input)
            .map(fetch)
            .onEach { runCatching { set(input, it) } }

        flowOf(getting, fetchingAndSetting).flattenConcat()
    }