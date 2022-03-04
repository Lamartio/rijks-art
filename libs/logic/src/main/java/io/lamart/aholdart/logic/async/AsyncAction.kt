package io.lamart.aholdart.logic

import io.lamart.aholdart.logic.async.Async
import io.lamart.aholdart.logic.async.executingOf
import io.lamart.aholdart.logic.async.failureOf
import io.lamart.aholdart.logic.async.successOf
import io.lamart.aholdart.optics.OptionalSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


suspend fun <P : Any, T : Any> CoroutineScope.asyncActionOf(
    source: OptionalSource<Async<P, T>>,
    transform: suspend (payload: P) -> Flow<T>,
    strategy: AsyncStrategy<P, T> = latest(),
    count: Int? = null,
): (payload: P) -> Unit {
    val channel = Channel<P>()
    val flow = channel
        .receiveAsFlow()
        .strategy(asyncFlowOf(transform))

    launch {
        flow
            .let {
                when (count) {
                    null -> it
                    else -> it.take(count.toInt())
                }
            }
            .collect(source::set)
    }

    return { payload ->
        channel.trySendBlocking(payload)
    }
}

fun <P, T> ofSuspending(transform: suspend (payload: P) -> T): suspend (payload: P) -> Flow<T> =
    { payload ->
        flow {
            payload
                .let { transform(it) }
                .let { emit(it) }
        }
    }

private fun <P : Any, T : Any> asyncFlowOf(transform: suspend (payload: P) -> Flow<T>): suspend (payload: P) -> Flow<Async<P, T>> =
    { payload ->
        transform(payload)
            .map { result -> successOf(payload, result) }
            .catch { reason -> emit(failureOf(payload, reason)) }
            .onStart { emit(executingOf(payload)) }
    }