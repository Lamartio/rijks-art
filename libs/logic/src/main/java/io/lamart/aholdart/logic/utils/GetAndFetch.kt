package io.lamart.aholdart.logic.utils

import kotlinx.coroutines.flow.*

fun <P, T : Any> getAndFetch(
    get: suspend (payload: P) -> T?,
    set: suspend (value: T) -> Unit,
    fetch: suspend (payload: P) -> T,
    onError: (error: Throwable) -> Unit = {},
): (payload: P) -> Flow<T> =
    { payload ->
        flowOf(Type.Get to payload, Type.Fetch to payload)
            .flatMapConcat { (type, payload) ->
                when (type) {
                    Type.Get -> flowOf(payload, get)
                    Type.Fetch -> flowOf(payload, fetch)
                        .onEach { result -> result.onSuccess { runCatching { set(it) } } }
                }
            }
            .onEach { result -> result.onFailure { error -> error.runCatching(onError) } }
            .mapNotNull { result -> result.getOrNull() }
    }

private enum class Type {
    Get,
    Fetch
}

private fun <P, T : Any> flowOf(payload: P, get: suspend (payload: P) -> T?): Flow<Result<T>> =
    flowOf(payload)
        .mapNotNull(get)
        .map { Result.success(it) }
        .catch { emit(Result.failure(it)) }
