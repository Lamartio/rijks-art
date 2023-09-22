package io.lamart.rijksart

import io.lamart.lux.Mutable
import io.lamart.lux.focus.FocusedLens
import io.lamart.rijksart.logic.RijksDepedencies
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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

internal fun <S> transaction(block: (focus: FocusedLens<*, S>) -> Unit): (S) -> S =
    { value ->
        Mutable(value)
            .apply { block(lens) }
            .get()
    }

internal fun <T, R> it(transform: T.() -> R): (T) -> R = transform

internal fun <T> Flow<T>.record(initial: T): Flow<Pair<T, T>> =
    this
        .map { it to it }
        .scan(initial to initial) { (_, old), (new) -> old to new }
        .drop(1)