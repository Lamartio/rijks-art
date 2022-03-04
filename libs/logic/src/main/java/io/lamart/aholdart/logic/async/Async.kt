package io.lamart.aholdart.logic.async

sealed class Async<P : Any, T : Any>(
    open val payload: P?,
    open val reason: Throwable?,
    open val result: T?,
) {
    // needs to be data class for supporting '=='
    data class Initial<P : Any, T : Any>(private val none: Unit = Unit) :
        Async<P, T>(null, null, null)

    data class Executing<P : Any, T : Any>(override val payload: P) :
        Async<P, T>(payload, null, null)

    data class Failure<P : Any, T : Any>(override val payload: P, override val reason: Throwable) :
        Async<P, T>(payload, reason, null)

    data class Success<P : Any, T : Any>(override val payload: P, override val result: T) :
        Async<P, T>(payload, null, result)

}

fun <P : Any, T : Any> initial(): Async<P, T> =
    Async.Initial()

fun <P : Any, T : Any> executingOf(payload: P): Async<P, T> =
    Async.Executing(payload)

fun <P : Any, T : Any> failureOf(payload: P, reason: Throwable): Async<P, T> =
    Async.Failure(payload, reason)

fun <P : Any, T : Any> successOf(payload: P, result: T): Async<P, T> =
    Async.Success(payload, result)