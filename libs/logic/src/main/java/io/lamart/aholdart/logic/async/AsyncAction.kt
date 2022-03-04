package io.lamart.aholdart.logic

import io.lamart.aholdart.logic.async.Async
import io.lamart.aholdart.logic.async.executing
import io.lamart.aholdart.logic.async.failureOf
import io.lamart.aholdart.logic.async.successOf
import io.lamart.aholdart.optics.OptionalSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

fun <P, T : Any> OptionalSource<Async<T>>.toAsyncAction(
    strategy: AsyncStrategy<P, T>,
    scope: CoroutineScope,
): (payload: P) -> Unit {
    val flow = MutableSharedFlow<P>(1)

    flow
        .strategy()
        .collectOn(scope, this@toAsyncAction::set)

    return {
        flow.tryEmit(it)
    }
}

private fun <T> Flow<T>.collectOn(scope: CoroutineScope, next: (value: T) -> Unit): Job {
    return scope.launch {
        collect(next)
    }
}

fun <P, T> flowOfSuspending(transform: suspend (payload: P) -> T): suspend (payload: P) -> Flow<T> =
    { payload ->
        flow {
            emit(transform(payload))
        }
    }

fun <P, T> asyncFlowOf(transform: suspend (payload: P) -> Flow<T>): suspend (payload: P) -> Flow<Async<T>> =
    { payload ->
        transform(payload)
            .map { result -> successOf(result) }
            .catch { reason -> emit(failureOf(reason)) }
            .onStart { emit(executing()) }
    }