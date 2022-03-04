package io.lamart.aholdart.logic

import io.lamart.aholdart.logic.async.Async
import kotlinx.coroutines.flow.*

typealias AsyncStrategy<P, T> = Flow<P>.() -> Flow<Async<T>>

fun <P, T> latest(transform: suspend (payload: P) -> Flow<T>): AsyncStrategy<P, T> =
    { flatMapLatest(asyncFlowOf(transform)) }

fun <P, T> concat(transform: suspend (payload: P) -> Flow<T>): AsyncStrategy<P, T> =
    { flatMapConcat(asyncFlowOf(transform)) }

fun <P, T> merge(transform: suspend (payload: P) -> Flow<T>): AsyncStrategy<P, T> =
    { flatMapMerge(DEFAULT_CONCURRENCY, asyncFlowOf(transform)) }