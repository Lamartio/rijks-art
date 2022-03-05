package io.lamart.aholdart.logic.utils

import kotlinx.coroutines.flow.*

private enum class Type {
    Get,
    Fetch
}

fun <P, T : Any> getAndFetch(
    get: suspend (payload: P) -> T?,
    set: suspend (value: T) -> Unit,
    fetch: suspend (payload: P) -> T,
): (payload: P) -> Flow<T> {
    return { payload ->
        flowOf(Type.Get to payload, Type.Fetch to payload)
            .flatMapConcat { (type, payload) ->
                when (type) {
                    Type.Get -> flowOf(payload).map(get).filterNotNull().map { type to it }
                    Type.Fetch -> flowOf(payload).map(fetch).map { type to it }
                }
            }
            .onEach { (type, result) -> if (type == Type.Fetch) set(result) }
            .map { (_, result) -> result }
    }
}