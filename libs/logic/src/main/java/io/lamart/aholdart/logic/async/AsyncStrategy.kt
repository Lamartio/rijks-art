package io.lamart.aholdart.logic

import io.lamart.aholdart.logic.async.Async
import kotlinx.coroutines.flow.*

typealias AsyncStrategy<P, T> = Flow<P>.(transform: suspend (payload: P) -> Flow<Async<P, T>>) -> Flow<Async<P, T>>

fun <P : Any, T : Any> latest(): AsyncStrategy<P, T> = { flatMapLatest(it) }
fun <P : Any, T : Any> concat(): AsyncStrategy<P, T> = { flatMapConcat(it) }
fun <P : Any, T : Any> merge(): AsyncStrategy<P, T> = { flatMapMerge(DEFAULT_CONCURRENCY, it) }