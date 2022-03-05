package io.lamart.aholdart.optics.async

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <P, T> flowOfSuspending(transform: suspend (payload: P) -> T): suspend (payload: P) -> Flow<T> =
    { payload ->
        flow {
            emit(transform(payload))
        }
    }